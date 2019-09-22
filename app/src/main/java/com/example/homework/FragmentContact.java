package com.example.homework;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class FragmentContact extends Fragment {

    private View view;
    private RecyclerView myRecyclerView;
    private ArrayList<Contact> lstContact;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Disposable disposable;
    private String name;
    private String Id;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("lstContact", lstContact);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_fragment, container, false);
        myRecyclerView = view.findViewById(R.id.contact_recyclerview);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), lstContact);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            lstContact = new ArrayList<>();
            getContacts();
        } else {
            lstContact = savedInstanceState.getParcelableArrayList("lstContact");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        myRecyclerView = null;
        recyclerViewAdapter = null;
        Id = null;
        name = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void getContacts() {
        disposable = getContactsObs()
                .subscribeOn(Schedulers.io())
                .doOnNext(cursor -> Id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)))
                .doOnNext(cursor -> name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)))
                .doOnNext(cursor -> getContactsSingle(name, Id).subscribe(new DisposableSingleObserver<Contact>() {
                    @Override
                    public void onSuccess(Contact contact) {
                        lstContact.add(contact);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private Observable<Cursor> getContactsObs() {
        return Observable.create(e -> {
            Cursor cursor = getActivity().getContentResolver()
                    .query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            try {
                if (cursor != null)
                    while (cursor.moveToNext()) {
                        e.onNext(cursor);
                    }
            } finally {
                if (cursor != null) cursor.close();
            }
        });
    }

    private Single<Contact> getContactsSingle(String name, String Id) {
        return Single.create(emitter ->
                emitter.onSuccess(new Contact(name, getPhoneF(Id),
                        getEmailF(Id), BitmapFactory.decodeStream(openPhoto(Id)))));
    }

    private String getPhoneF(String id) {
        Cursor cursor = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        + " = ?", new String[]{id}, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToNext()) {

                return cursor
                        .getString(cursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    private String getEmailF(String Id) {
        Cursor cursor = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID
                        + " = ?", new String[]{Id}, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToNext()) {
                return cursor
                        .getString(cursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    private InputStream openPhoto(String contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getActivity().getContentResolver().query(photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return new ByteArrayInputStream(data);
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }
}
