package com.example.homework.di.modules;


import com.example.homework.domain.repository.ContactsRepository;
import com.example.homework.domain.interactor.InfoInteractorImpl;
import com.example.homework.di.scopes.ContactInfoScreenScope;
import com.example.homework.domain.interactor.InfoInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class InfoInteractorModule {

    @Provides
    @ContactInfoScreenScope
    InfoInteractor provideInfoInteractor(ContactsRepository contactsRepository) {
        return new InfoInteractorImpl(contactsRepository);
    }
}
