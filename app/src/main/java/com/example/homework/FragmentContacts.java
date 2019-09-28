package com.example.homework;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import presenters.ContactsPresenter;
import views.ContactsView;

import static android.content.ContentValues.TAG;


public class FragmentContacts extends MvpFragment implements ContactsView {
    @InjectPresenter
    ContactsPresenter contactsPresenter;

    private View view;
    private RecyclerView myRecyclerView;
//    private ArrayList<Contact> lstContact;
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
        myRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return view;

    }

//    public static FragmentContactInfo newInstance() {
//        FragmentContacts myFragment = new FragmentContacts();
//        Bundle bundle = new Bundle();
//        myFragment.setArguments(bundle);
//        return myFragment;
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactsPresenter.getContacts();
    }

    @Override
    public void setContacts(ContactsProvider contactsProvider) {
        contactsProvider = new ContactsProvider(getActivity().getContentResolver());
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),contactsProvider.getContacts());

    //    recyclerViewAdapter.notifyDataSetChanged();

    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        contactsPresenter.getContacts();
//        //TODO вынести запрос разрешений
//        if (savedInstanceState == null) {
//            lstContact = new ArrayList<>();
//            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
//                    Manifest.permission.READ_CONTACTS)
//                    != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
//                        1);
//            } else {
//                getContacts();
//            }
//        } else {
//            lstContact = savedInstanceState.getParcelableArrayList("lstContact");
//        }
//    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 1: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getContacts();
//                } else {
//                    Toast.makeText(getContext(), "Второго шанса не будет", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        myRecyclerView = null;
        recyclerViewAdapter = null;
    }






}
