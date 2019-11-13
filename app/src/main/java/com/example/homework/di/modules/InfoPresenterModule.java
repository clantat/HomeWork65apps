package com.example.homework.di.modules;

import androidx.annotation.NonNull;

import com.example.homework.domain.interactor.InfoInteractor;
import com.example.homework.presentation.fragment.FragmentContactInfo;
import com.example.homework.presentation.presenters.InfoPresenter;
import com.example.homework.di.scopes.ContactInfoScreenScope;
import com.example.homework.room.AppDatabase;

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
    InfoPresenter provideInfoPresenter(AppDatabase appDatabase, InfoInteractor infoInteractor, @Named("contact_id") String id) {
        return new InfoPresenter(appDatabase, infoInteractor, id);
    }

    @ContactInfoScreenScope
    @Provides
    @Named("contact_id")
    String provideContactId() {
        return Objects.requireNonNull(Objects.requireNonNull(fragmentContactInfo.getArguments()).getString("id"));
    }
}
