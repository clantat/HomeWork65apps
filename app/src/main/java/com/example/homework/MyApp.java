package com.example.homework;

import android.app.Application;

import com.example.homework.components.AppComponent;
import com.example.homework.components.DaggerAppComponent;
import com.example.homework.components.FragmentContactsComponent;
import com.example.homework.components.FragmentInfoComponent;
import com.example.homework.modules.AppModule;
import com.example.homework.modules.ContactsPresenterModule;
import com.example.homework.modules.InfoPresenterModule;

public class MyApp extends Application {
    protected static MyApp instance;

    private AppComponent appComponent;
    private FragmentContactsComponent fragmentContactsComponent;
    private FragmentInfoComponent fragmentInfoComponent;

    public static MyApp get() {
        return instance;
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
            fragmentContactsComponent = appComponent.plusFragmentContactsComponent(new ContactsPresenterModule());
        return fragmentContactsComponent;
    }

    public void clearFragmentContactsComponent() {
        fragmentContactsComponent = null;
    }

    public FragmentInfoComponent plusFragmentInfoComponent() {
        if (fragmentInfoComponent == null)
            fragmentInfoComponent = appComponent.plusFragmentInfoComponent(new InfoPresenterModule());
        return fragmentInfoComponent;
    }

    public void clearFragmentInfoComponent() {
        fragmentInfoComponent = null;
    }

}
