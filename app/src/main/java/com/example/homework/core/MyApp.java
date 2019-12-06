package com.example.homework.core;

import android.app.Application;
import android.content.Context;

import com.example.homework.di.components.AppComponent;
import com.example.homework.di.components.DaggerAppComponent;
import com.example.homework.di.components.FragmentContactsComponent;
import com.example.homework.di.components.FragmentInfoComponent;
import com.example.homework.di.components.FragmentMapComponent;
import com.example.homework.di.modules.AppModule;
import com.example.homework.di.modules.ContactsInteractorModule;
import com.example.homework.di.modules.ContactsPresenterModule;
import com.example.homework.di.modules.InfoInteractorModule;
import com.example.homework.di.modules.InfoPresenterModule;
import com.example.homework.di.modules.mapscreen.GeoCodingServiceModule;
import com.example.homework.di.modules.mapscreen.MapContactProviderModule;
import com.example.homework.di.modules.mapscreen.MapDatabaseModule;
import com.example.homework.di.modules.mapscreen.MapInteractorModule;
import com.example.homework.di.modules.mapscreen.MapPresenterModule;
import com.example.homework.di.modules.mapscreen.MapRepositoryModule;
import com.example.homework.presentation.fragment.FragmentContactInfo;

public class MyApp extends Application {
    protected static MyApp instance;

    //Components

    private AppComponent appComponent;
    private FragmentContactsComponent fragmentContactsComponent;
    private FragmentInfoComponent fragmentInfoComponent;
    private FragmentMapComponent fragmentMapComponent;

    public static MyApp get() {
        return instance;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            DaggerAppComponent.builder().build();
        }
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }


    public FragmentContactsComponent plusFragmentContactsComponent() {
        if (fragmentContactsComponent == null)
            fragmentContactsComponent = appComponent.plusFragmentContactsComponent(new ContactsPresenterModule(),new ContactsInteractorModule());
        return fragmentContactsComponent;
    }

    public void clearFragmentContactsComponent() {
        fragmentContactsComponent = null;
    }

    public FragmentInfoComponent plusFragmentInfoComponent(FragmentContactInfo fragmentContactInfo) {
        if (fragmentInfoComponent == null)
            fragmentInfoComponent = appComponent.plusFragmentInfoComponent(new InfoPresenterModule(fragmentContactInfo),new InfoInteractorModule());
        return fragmentInfoComponent;
    }

    public void clearFragmentInfoComponent() {
        fragmentInfoComponent = null;
    }

    public FragmentMapComponent plusFragmentMapComponent() {
        if (fragmentMapComponent == null)
            fragmentMapComponent = appComponent.plusFragmentMapComponent(
                    new MapPresenterModule(),
                    new GeoCodingServiceModule(),
                    new MapContactProviderModule(),
                    new MapRepositoryModule(),
                    new MapInteractorModule(),
                    new MapDatabaseModule());
        return fragmentMapComponent;
    }

    public void clearFragmentMapComponent() {
        fragmentMapComponent = null;
    }

}
