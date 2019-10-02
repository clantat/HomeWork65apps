package com.example.homework;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.homework.presenters.ContactsPresenter;
import com.example.homework.views.ContactsView;

import java.util.List;
import java.util.Objects;


public class FragmentContacts extends MvpAppCompatFragment implements ContactsView {
    @InjectPresenter
    ContactsPresenter contactsPresenter;

    private View view;
    private RecyclerView myRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    public FragmentContacts() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_fragment, container, false);
        myRecyclerView = view.findViewById(R.id.contact_recyclerview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @ProvidePresenter
    ContactsPresenter provideContactsPresenter() {
        return new ContactsPresenter(new ContactsProvider(getActivity().getContentResolver()));
    }

    @Override
    public void onRequestPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        1);
            }
        } else {
            contactsPresenter.getContacts();
        }
    }

    @Override
    public void setContacts(List<ShortContact> list) {
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerViewAdapter.setData(list);
        myRecyclerView.setAdapter(recyclerViewAdapter);
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
