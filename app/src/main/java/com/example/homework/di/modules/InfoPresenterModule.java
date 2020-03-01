package com.example.homework.di.modules;

import androidx.annotation.NonNull;

import com.example.homework.domain.interactor.InfoInteractor;
import com.example.homework.presentation.fragment.FragmentContactInfo;
import com.example.homework.presentation.presenters.InfoPresenter;
import com.example.homework.di.scopes.ContactInfoScreenScope;
import com.example.homework.schedulers.SchedulerManager;

import java.util.Objects;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Router;

@Module
public class InfoPresenterModule {
    @NonNull
    private final FragmentContactInfo fragmentContactInfo;

    public InfoPresenterModule(@NonNull final FragmentContactInfo fragmentContactInfo) {
        this.fragmentContactInfo = fragmentContactInfo;
    }

    @ContactInfoScreenScope
    @Provides
    InfoPresenter provideInfoPresenter(InfoInteractor infoInteractor, @Named("contact_id") String id, SchedulerManager schedulerManager, Router router) {
        return new InfoPresenter(infoInteractor, id, schedulerManager, router);
    }

    @ContactInfoScreenScope
    @Provides
    @Named("contact_id")
    String provideContactId() {
        return Objects.requireNonNull(Objects.requireNonNull(fragmentContactInfo.getArguments()).getString("id"));
    }

}
