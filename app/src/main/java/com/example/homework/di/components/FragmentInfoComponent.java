package com.example.homework.di.components;

import com.example.homework.di.modules.InfoInteractorModule;
import com.example.homework.presentation.fragment.FragmentContactInfo;
import com.example.homework.di.modules.InfoPresenterModule;
import com.example.homework.di.scopes.ContactInfoScreenScope;

import dagger.Subcomponent;

@Subcomponent(modules = {InfoPresenterModule.class, InfoInteractorModule.class})
@ContactInfoScreenScope
public interface FragmentInfoComponent {
    void inject(FragmentContactInfo fragmentContactInfo);
}
