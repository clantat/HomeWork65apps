package com.example.homework.di.components;


import com.example.homework.di.modules.ContactsInteractorModule;
import com.example.homework.di.modules.ContactsProviderModule;
import com.example.homework.di.modules.ContactsRepositoryModule;
import com.example.homework.di.modules.InfoInteractorModule;
import com.example.homework.core.MainActivity;
import com.example.homework.di.modules.AppModule;
import com.example.homework.di.modules.ContactsPresenterModule;
import com.example.homework.di.modules.ContentResolverModule;
import com.example.homework.di.modules.InfoPresenterModule;
import com.example.homework.di.modules.NavigationModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, ContentResolverModule.class,
        ContactsProviderModule.class, NavigationModule.class, ContactsRepositoryModule.class})
@Singleton
public interface AppComponent {

    void inject(MainActivity mainActivity);

    FragmentContactsComponent plusFragmentContactsComponent(ContactsPresenterModule contactsPresenterModule,
                                                            ContactsInteractorModule contactsInteractorModule);

    FragmentInfoComponent plusFragmentInfoComponent(InfoPresenterModule infoPresenterModule,
                                                    InfoInteractorModule infoInteractorModule);
}
