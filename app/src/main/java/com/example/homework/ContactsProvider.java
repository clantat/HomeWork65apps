package com.example.homework;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class ContactsProvider {
    private ArrayList<Contact> lstContact;
    private ContentResolver contentResolver;
    private Disposable disposable;

    public ContactsProvider() {
    }

    public ContactsProvider(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public ArrayList<Contact> getContacts() {
        lstContact = new ArrayList<>();
        disposable = getContactsObs()
                .subscribeOn(Schedulers.io())
                .flatMapSingle(this::getContactsSingle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    lstContact.add(item);
                    Log.i(TAG, "getContacts:" + lstContact.get(0).getName());
                });
        return lstContact;
    }

    public void unsubscribe() {
        disposable.dispose();
    }

//    public static ContactsProvider newInstance(ContentResolver contentResolver) {
//        return new ContactsProvider(contentResolver);
//
//    }


    private Observable<String> getContactsObs() {
        return Observable.create(e -> {
            Cursor cursor = contentResolver
                    .query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (cursor != null)
                try {
                    while (cursor.moveToNext()) {
                        e.onNext(cursor.getString(cursor
                                .getColumnIndex(ContactsContract.Contacts._ID)));
                    }
                } finally {
                    cursor.close();
                }
        });
    }

    private Single<Contact> getContactsSingle(String Id) {
        return Single.fromCallable(() -> new Contact(getNameF(Id), getPhoneF(Id),
                getEmailF(Id), BitmapFactory.decodeStream(openPhoto(Id))));
    }

    private String getNameF(String id) {
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts._ID
                        + " = ?", new String[]{id}, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToNext()) {

                return cursor
                        .getString(cursor
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    private String getPhoneF(String id) {
        Cursor cursor = contentResolver.query(
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
        Cursor cursor = contentResolver.query(
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
        Cursor cursor = contentResolver.query(photoUri,
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
