package com.example.homework.di.modules.mapscreen;

import android.content.Context;

import androidx.room.Room;

import com.example.homework.data.room.MapDatabase;
import com.example.homework.di.scopes.MapScreenScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MapDatabaseModule {

    @Provides
    @MapScreenScope
    public MapDatabase provideDataBase(Context context) {
        return Room.databaseBuilder(context, MapDatabase.class, "db")
                .build();
    }

}
