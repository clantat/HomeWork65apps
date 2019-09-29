package com.example.homework;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.ArrayList;
import java.util.Objects;

import presenters.ContactsPresenter;
import views.ContactsView;

import static android.content.ContentValues.TAG;


public class FragmentContacts extends MvpFragment implements ContactsView {
    @InjectPresenter

    ContactsPresenter contactsPresenter;

    private View view;
    private RecyclerView myRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList("lstContact", lstContact);
//    }


    public FragmentContacts() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.contact_fragment, container, false);
        myRecyclerView = view.findViewById(R.id.contact_recyclerview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return view;

    }

//    public static FragmentContactInfo newInstance() {
//        FragmentContacts myFragment = new FragmentContacts();
//        Bundle bundle = new Bundle();
//        myFragment.setArguments(bundle);
//        return myFragment;
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        contactsPresenter.getContacts();
//
//    }

    @ProvidePresenter
    ContactsPresenter provideContactsPresenter() {
        Log.i(TAG, "getContacts: providePresenter");
        return new ContactsPresenter(new ContactsProvider(getActivity().getContentResolver()));
    }

    @Override
    public void setContacts(ArrayList<Contact> arrayList) {
        Log.i(TAG, "getContacts: setContacts");
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), arrayList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                    Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                            1);
                }
            } else {
                Log.i(TAG, "getContacts: oncreate");
                contactsPresenter.getContacts();
            }
        } else {
            //TODO взять инфу из state
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    contactsPresenter.getContacts();
                } else {
                    Toast.makeText(getActivity(), "Второго шанса не будет", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        myRecyclerView = null;
        recyclerViewAdapter = null;
    }


}
