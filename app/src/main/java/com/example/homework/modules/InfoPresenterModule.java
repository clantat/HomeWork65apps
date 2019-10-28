package com.example.homework.modules;

import androidx.annotation.NonNull;

import com.example.homework.ContactsProvider;
import com.example.homework.FragmentContactInfo;
import com.example.homework.presenters.InfoPresenter;
import com.example.homework.scopes.ContactInfoScreenScope;

import java.util.Objects;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class InfoPresenterModule {
    @NonNull
    private final FragmentContactInfo fragmentContactInfo;

    public InfoPresenterModule(@NonNull final FragmentContactInfo fragmentContactInfo) {
        this.fragmentContactInfo = fragmentContactInfo;
    }

    @ContactInfoScreenScope
    @Provides
    InfoPresenter provideInfoPresenter(ContactsProvider contactsProvider, @Named("contact_id") String id) {
        return new InfoPresenter(contactsProvider,id);
    }

    @ContactInfoScreenScope
    @Provides
    @Named("contact_id")
    String provideContactId() {
        return Objects.requireNonNull(Objects.requireNonNull(fragmentContactInfo.getArguments()).getString("id"));
    }
}
