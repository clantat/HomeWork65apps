package com.example.homework;

import android.app.Fragment;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            Fragment firstFragment = new FragmentContacts();
            firstFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.container, firstFragment).commit();
        }

    }

}
