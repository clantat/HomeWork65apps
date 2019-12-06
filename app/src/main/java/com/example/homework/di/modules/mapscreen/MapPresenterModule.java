package com.example.homework.di.modules.mapscreen;

import com.example.homework.di.scopes.MapScreenScope;
import com.example.homework.domain.interactor.MapInteractor;
import com.example.homework.presentation.presenters.MapPresenter;
import com.example.homework.schedulers.SchedulerManager;

import dagger.Module;
import dagger.Provides;
@Module
public class MapPresenterModule {
    @Provides
    @MapScreenScope
    MapPresenter provideMapPresenter(MapInteractor mapInteractor, SchedulerManager schedulerManager){
        return new MapPresenter(mapInteractor, schedulerManager);
    }
}
