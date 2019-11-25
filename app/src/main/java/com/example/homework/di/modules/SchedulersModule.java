package com.example.homework.di.modules;

import com.example.homework.schedulers.SchedulerManager;
import com.example.homework.schedulers.SchedulerManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulersModule {

    @Provides
    @Singleton
    SchedulerManager provideSchedulerManager(){
        return new SchedulerManagerImpl();
    }
}
