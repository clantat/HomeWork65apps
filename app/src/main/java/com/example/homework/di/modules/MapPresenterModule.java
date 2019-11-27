package com.example.homework.di.modules;

import com.example.homework.di.scopes.MapScreenScope;
import com.example.homework.presentation.presenters.MapPresenter;

import dagger.Module;
import dagger.Provides;
@Module
public class MapPresenterModule {
    @Provides
    @MapScreenScope
    MapPresenter provideMapPresenter(){
        return new MapPresenter();
    }
}
