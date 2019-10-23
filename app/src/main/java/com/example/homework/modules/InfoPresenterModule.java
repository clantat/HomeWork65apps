package com.example.homework.modules;

import com.example.homework.ContactsProvider;
import com.example.homework.presenters.InfoPresenter;
import com.example.homework.scopes.ContactInfoScreenScope;

import dagger.Module;
import dagger.Provides;

@Module
public class InfoPresenterModule {
    @ContactInfoScreenScope
    @Provides
    InfoPresenter provideInfoPresenter(ContactsProvider contactsProvider){
        return new InfoPresenter(contactsProvider);
    }
}
