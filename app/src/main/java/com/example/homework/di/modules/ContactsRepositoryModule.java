package com.example.homework.di.modules;

import com.example.homework.data.provider.ContactsProvider;
import com.example.homework.domain.repository.ContactsRepository;
import com.example.homework.data.repository.ContactsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsRepositoryModule {
    @Provides
    @Singleton
    ContactsRepository provideContactsRepository(ContactsProvider contactsProvider){
        return new ContactsRepositoryImpl(contactsProvider);
    }
}
