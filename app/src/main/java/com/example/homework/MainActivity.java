package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private FragmentContact fragmentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            fragmentContact = new FragmentContact();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, fragmentContact)
                    .commit();
        } else {
            fragmentContact = (FragmentContact) getSupportFragmentManager().getFragment(savedInstanceState, "fragmentContact");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "fragmentContact", fragmentContact);
    }
}
