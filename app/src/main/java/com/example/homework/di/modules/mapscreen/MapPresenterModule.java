package com.example.homework.di.modules.mapscreen;

import androidx.annotation.NonNull;

import com.example.homework.di.scopes.ContactInfoScreenScope;
import com.example.homework.di.scopes.MapScreenScope;
import com.example.homework.domain.interactor.MapInteractor;
import com.example.homework.presentation.fragment.MapFragment;
import com.example.homework.presentation.presenters.MapPresenter;
import com.example.homework.schedulers.SchedulerManager;

import java.util.Objects;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
@Module
public class MapPresenterModule {
    @NonNull
    private final MapFragment mapFragment;

    public MapPresenterModule(@NonNull final MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Provides
    @MapScreenScope
    MapPresenter provideMapPresenter(MapInteractor mapInteractor, SchedulerManager schedulerManager,@Named("contactId")String id){
        return new MapPresenter(mapInteractor, schedulerManager, id);
    }

    @MapScreenScope
    @Provides
    @Named("contactId")
    String provideContactId() {
        return Objects.requireNonNull(Objects.requireNonNull(mapFragment.getArguments()).getString("id"));
    }
}
