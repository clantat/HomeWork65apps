package com.example.homework.components;


import com.example.homework.MainActivity;
import com.example.homework.modules.AppModule;
import com.example.homework.modules.ContactsPresenterModule;
import com.example.homework.modules.ContactsProviderModule;
import com.example.homework.modules.ContentResolverModule;
import com.example.homework.modules.InfoPresenterModule;
import com.example.homework.modules.NavigationModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, ContentResolverModule.class,
        ContactsProviderModule.class, NavigationModule.class})
@Singleton
public interface AppComponent {

    void inject(MainActivity mainActivity);

    FragmentContactsComponent plusFragmentContactsComponent(ContactsPresenterModule contactsPresenterModule);

    FragmentInfoComponent plusFragmentInfoComponent(InfoPresenterModule infoPresenterModule);
}
