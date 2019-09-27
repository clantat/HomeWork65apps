package com.example.homework;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.container) != null) {

            if (savedInstanceState != null) {
                return;

            }
            //TODO связать с фрагментом
            FragmentContacts firstFragment = new FragmentContacts();
//            firstFragment.setArguments(getIntent().getExtras());
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, firstFragment).commit();
        }

    }

}
