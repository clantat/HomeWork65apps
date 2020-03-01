package com.example.homework.core;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework.core.MyApp;
import com.example.homework.R;
import com.example.homework.core.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public class MainActivity extends AppCompatActivity {

    @Inject
    Router router;
    @Inject
    NavigatorHolder navigatorHolder;

    private Navigator navigator = new SupportAppNavigator(this, R.id.container);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApp.get().getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            router.newRootScreen(new Screens.ContactsScreen());
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }
}
