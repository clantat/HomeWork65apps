package com.example.homework.modules;

import com.example.homework.ContactsProvider;
import com.example.homework.presenters.ContactsPresenter;
import com.example.homework.scopes.ContactsScreenScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsPresenterModule {

    @Provides
    @ContactsScreenScope
    public ContactsPresenter provideContactsPresenter(ContactsProvider contactsProvider) {
        return new ContactsPresenter(contactsProvider);
    }
}
