package com.example.homework.di.modules.mapscreen;

import com.example.homework.di.scopes.MapScreenScope;
import com.example.homework.domain.interactor.MapInteractor;
import com.example.homework.domain.interactor.MapInteractorImpl;
import com.example.homework.domain.repository.MapRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class MapInteractorModule {
    @MapScreenScope
    @Provides
    MapInteractor provideMapInteractor(MapRepository mapRepository){
        return new MapInteractorImpl(mapRepository);
    }
}
