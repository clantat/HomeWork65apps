package com.example.homework.di.modules;

import com.example.homework.domain.interactor.ContactsInteractor;
import com.example.homework.presentation.presenters.ContactsPresenter;
import com.example.homework.di.scopes.ContactsScreenScope;
import com.example.homework.schedulers.SchedulerManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsPresenterModule {

    @Provides
    @ContactsScreenScope
    ContactsPresenter provideContactsPresenter(ContactsInteractor contactsInteractor, SchedulerManager schedulerManager) {
        return new ContactsPresenter(contactsInteractor,schedulerManager);
    }
}
