package com.example.homework;

import android.app.Application;

import com.example.homework.components.AppComponent;
import com.example.homework.components.ContactsPresenterComponent;
import com.example.homework.components.DaggerAppComponent;
import com.example.homework.modules.AppModule;
import com.example.homework.modules.ContactsPresenterModule;

public class MyApp extends Application {
    protected static MyApp instance;

    private AppComponent appComponent;
    private ContactsPresenterComponent contactsPresenterComponent;

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

    public ContactsPresenterComponent plusContactsPresenterComponent() {
        if (contactsPresenterComponent == null)
            contactsPresenterComponent = appComponent.plusContactsPresenterComponent(new ContactsPresenterModule());
        return contactsPresenterComponent;
    }

    public void ClearContactsPresenterComponent (){
        contactsPresenterComponent = null;
    }
}
