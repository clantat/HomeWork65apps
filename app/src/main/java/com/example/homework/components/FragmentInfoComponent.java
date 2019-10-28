package com.example.homework.components;

import com.example.homework.FragmentContactInfo;
import com.example.homework.modules.InfoPresenterModule;
import com.example.homework.scopes.ContactInfoScreenScope;

import dagger.Subcomponent;

@Subcomponent(modules = InfoPresenterModule.class)
@ContactInfoScreenScope
public interface FragmentInfoComponent {
    void inject(FragmentContactInfo fragmentContactInfo);
}
