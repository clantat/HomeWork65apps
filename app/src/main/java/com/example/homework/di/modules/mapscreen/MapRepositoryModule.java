package com.example.homework.di.modules.mapscreen;

import com.example.homework.data.provider.MapContactProvider;
import com.example.homework.data.repository.MapRepositoryImpl;
import com.example.homework.di.scopes.MapScreenScope;
import com.example.homework.domain.repository.MapRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class MapRepositoryModule {
    @MapScreenScope
    @Provides
    MapRepository provideMapRepository(MapContactProvider mapContactProvider){
        return new MapRepositoryImpl(mapContactProvider);
    }
}
