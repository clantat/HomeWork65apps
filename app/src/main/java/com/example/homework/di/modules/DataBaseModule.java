package com.example.homework.di.modules;

import android.content.Context;

import androidx.room.Room;

import com.example.homework.room.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {
    @Provides
    @Singleton
    public AppDatabase provideDataBase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database").build();
    }
}
