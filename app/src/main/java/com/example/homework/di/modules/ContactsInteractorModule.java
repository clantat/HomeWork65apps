package com.example.homework.di.modules;

import com.example.homework.domain.interactor.ContactsInteractorImpl;
import com.example.homework.data.repository.ContactsRepository;
import com.example.homework.di.scopes.ContactsScreenScope;
import com.example.homework.domain.interactor.ContactsInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsInteractorModule{

    @Provides
    @ContactsScreenScope
    ContactsInteractor provideContactsInteractor(ContactsRepository contactsRepository){
        return new ContactsInteractorImpl(contactsRepository);
    }
}
