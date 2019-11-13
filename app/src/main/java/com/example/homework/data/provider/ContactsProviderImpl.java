package com.example.homework.data.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.example.homework.data.provider.ContactsProvider;
import com.example.homework.domain.model.Contact;
import com.example.homework.domain.model.ShortContact;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class ContactsProviderImpl implements ContactsProvider {

    private final ContentResolver contentResolver;

    @Inject
    public ContactsProviderImpl(ContentResolver contentResolver){
        this.contentResolver = contentResolver;
    }

    public Single<List<ShortContact>> getContacts(String searchText) {
        return searchName(searchText)
                .flatMapSingle(this::getShortContact)
                .toList();
    }

    public Single<Contact> getInfoContact(String Id) {
        return Single.fromCallable(() -> new Contact(Id, getNameF(Id), getPhoneF(Id),
                getEmailF(Id), openPhoto(Id)));
    }


    private Single<ShortContact> getShortContact(String Id) {
        return Single.fromCallable(() -> new ShortContact(Id, getNameF(Id), getPhoneF(Id)));
    }

    private Observable<String> searchName(String searchText) {
        return Observable.create(e -> {
            String[] projection = {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME};
            Cursor cursor = contentResolver
                    .query(ContactsContract.Contacts.CONTENT_URI, projection,
                            ContactsContract.Contacts.DISPLAY_NAME + " LIKE '" + searchText + "%'",
                            null, null);
            if (cursor != null)
                try {
                    while (cursor.moveToNext()) {
                        if (!e.isDisposed())
                            e.onNext(cursor.getString(cursor
                                    .getColumnIndex(ContactsContract.Contacts._ID)));
                        else return;
                    }
                    e.onComplete();
                } catch (Throwable throwable) {
                    e.onError(throwable);
                } finally {
                    cursor.close();
                }
        });
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

    private byte[] openPhoto(String contactId) {
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
                    return data;
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }
}
