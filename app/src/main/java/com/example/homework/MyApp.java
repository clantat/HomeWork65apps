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

    //Components

    private AppComponent appComponent;
    private FragmentContactsComponent fragmentContactsComponent;
    private FragmentInfoComponent fragmentInfoComponent;

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
            fragmentContactsComponent = appComponent.plusFragmentContactsComponent(new ContactsPresenterModule());
        return fragmentContactsComponent;
    }

    public void clearFragmentContactsComponent() {
        fragmentContactsComponent = null;
    }

    public FragmentInfoComponent plusFragmentInfoComponent(FragmentContactInfo fragmentContactInfo) {
        if (fragmentInfoComponent == null)
            fragmentInfoComponent = appComponent.plusFragmentInfoComponent(new InfoPresenterModule(fragmentContactInfo));
        return fragmentInfoComponent;
    }

    public void clearFragmentInfoComponent() {
        fragmentInfoComponent = null;
    }

}
