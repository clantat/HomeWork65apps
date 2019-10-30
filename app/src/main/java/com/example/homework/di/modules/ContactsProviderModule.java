package com.example.homework.di.modules;

import android.content.ContentResolver;

import com.example.homework.data.provider.ContactsProvider;
import com.example.homework.data.provider.ContactsProviderImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsProviderModule {

    @Provides
    @Singleton
    ContactsProvider provideContactsProvider(ContentResolver contentResolver){
        return new ContactsProviderImpl(contentResolver);
    }
}
