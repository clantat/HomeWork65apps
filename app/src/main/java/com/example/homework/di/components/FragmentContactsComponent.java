package com.example.homework.di.components;

import com.example.homework.di.modules.ContactsInteractorModule;
import com.example.homework.presentation.fragment.FragmentContacts;
import com.example.homework.di.modules.ContactsPresenterModule;
import com.example.homework.di.scopes.ContactsScreenScope;

import dagger.Subcomponent;

@Subcomponent(modules = {ContactsPresenterModule.class, ContactsInteractorModule.class})
@ContactsScreenScope
public interface FragmentContactsComponent {
    void inject(FragmentContacts fragmentContacts);
}
