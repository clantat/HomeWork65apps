package com.example.homework.modules;

import android.content.ContentResolver;

import com.example.homework.ContactsProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsProviderModule {

    @Provides
    @Singleton
    public ContactsProvider provideContactsProvider(ContentResolver contentResolver){
        return new ContactsProvider(contentResolver);
    }
}
