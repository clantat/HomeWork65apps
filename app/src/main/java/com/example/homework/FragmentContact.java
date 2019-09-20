package com.example.homework;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class FragmentContact extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<Contact> lstContact;
    private RecyclerViewAdapter recyclerViewAdapter;

    public FragmentContact() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int i =recyclerViewAdapter.getPosition();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.contact_fragment, container, false);
        myRecyclerView = v.findViewById(R.id.contact_recyclerview);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), lstContact);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            lstContact = new ArrayList<>();
        getContacts();
    }

    private void getContacts() {
        String name;
        String Id;
        long contactId;
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor curName = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (curName != null) {
            while (curName.moveToNext()) {
                name = curName.getString(curName.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Id = curName.getString(curName.getColumnIndex(ContactsContract.Contacts._ID));
                contactId = curName.getLong(curName.getColumnIndex(ContactsContract.Contacts._ID));
                getContactObs(name,Id,contactId).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe();
                Log.i(TAG, "ends");
            }
            curName.close();
        }


    }

    private Observable<List<Contact>> getContactObs(String name,String Id, Long contactId) {
        return Observable.create(observableEmitter -> {
            lstContact.add(new Contact(name,getPhoneF(Id),getEmailF(Id),BitmapFactory.decodeStream(openPhoto(contactId))));
        });
    }

    private String getPhoneF(String id){

        Cursor pCur = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        + " = ?", new String[] { id }, null);
        if (pCur !=null) {
            if (pCur.moveToNext()) {
                String phone = pCur
                        .getString(pCur
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                pCur.close();
                return phone;
            }
            pCur.close();
        }
        return null;
    }

    private String getEmailF(String Id) {
        Cursor emailCur = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID
                        + " = ?", new String[]{Id}, null);
        if (emailCur != null) {
            if (emailCur.moveToNext()) {
                String emailContact = emailCur
                        .getString(emailCur
                                .getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                emailCur.close();
                return emailContact;
            }
            emailCur.close();
        }
        return null;
    }

    private InputStream openPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
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
