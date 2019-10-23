package com.example.homework.components;


import com.example.homework.modules.AppModule;
import com.example.homework.modules.ContactsPresenterModule;
import com.example.homework.modules.ContactsProviderModule;
import com.example.homework.modules.ContentResolverModule;
import com.example.homework.modules.InfoPresenterModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, ContentResolverModule.class, ContactsProviderModule.class})
@Singleton
public interface AppComponent {

    FragmentContactsComponent plusFragmentContactsComponent(ContactsPresenterModule contactsPresenterModule);

    FragmentInfoComponent plusFragmentInfoComponent(InfoPresenterModule infoPresenterModule);
}
