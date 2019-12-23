package com.example.homework.di.modules.mapscreen;

import android.content.Context;

import com.example.homework.data.provider.MapContactProvider;
import com.example.homework.data.provider.MapContactProviderImpl;
import com.example.homework.data.room.MapDatabase;
import com.example.homework.di.scopes.MapScreenScope;
import com.example.homework.retrofitrequests.direction.DirectionService;
import com.example.homework.retrofitrequests.geocoding.GeoCodingService;

import dagger.Module;
import dagger.Provides;

@Module
public class MapContactProviderModule {
    @MapScreenScope
    @Provides
    MapContactProvider provideMapContactProvider(MapDatabase mapDatabase, GeoCodingService geoCodingService, Context context, DirectionService directionService){
        return new MapContactProviderImpl(mapDatabase,geoCodingService, context, directionService);
    }
}
