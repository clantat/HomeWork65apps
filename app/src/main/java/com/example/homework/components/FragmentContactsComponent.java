package com.example.homework.components;

import com.example.homework.FragmentContacts;
import com.example.homework.modules.ContactsPresenterModule;
import com.example.homework.scopes.ContactsScreenScope;

import dagger.Subcomponent;

@Subcomponent(modules = ContactsPresenterModule.class)
@ContactsScreenScope
public interface FragmentContactsComponent {
    void inject(FragmentContacts fragmentContacts);
}
