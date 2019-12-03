package com.example.homework.di.modules;

import android.content.Context;

import androidx.room.Room;

import com.example.homework.data.room.MapDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MapDatabaseModule {

    @Provides
    @Singleton
    public MapDatabase provideDataBase(Context context) {
        return Room.databaseBuilder(context, MapDatabase.class, "database").build();
    }

}
