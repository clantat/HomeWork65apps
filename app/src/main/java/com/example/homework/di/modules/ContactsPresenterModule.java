package com.example.homework.di.modules;

import com.example.homework.domain.interactor.ContactsInteractor;
import com.example.homework.presentation.presenters.ContactsPresenter;
import com.example.homework.di.scopes.ContactsScreenScope;
import com.example.homework.room.AppDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsPresenterModule {

    @Provides
    @ContactsScreenScope
    ContactsPresenter provideContactsPresenter(ContactsInteractor contactsInteractor, AppDatabase appDatabase) {
        return new ContactsPresenter(contactsInteractor, appDatabase);
    }
}
