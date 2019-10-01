package com.example.homework;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
//TODO раскидать методы в разные интерфейсы

public class ContactsProvider {
    private ContentResolver contentResolver;

    public ContactsProvider() {
    }

    public ContactsProvider(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public Single<List<Contact>> getContacts() {
        Log.i(TAG, "getContacts: getContacts in ContactsProvider");
        return getContactsObs()
                .subscribeOn(Schedulers.io())
                .flatMapSingle(this::getContactsSingle)
                .toList();
    }

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
                String curName = cursor
                        .getString(cursor
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.i(TAG, "getContacts: curName: " + curName);
                return curName;
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
