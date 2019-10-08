package com.example.homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.homework.presenters.ContactsPresenter;
import com.example.homework.views.ContactsView;

import java.util.List;


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
    public void onRequestPermission(RequestPermissionFragment requestPermissionFragment) {
        if (requestPermissionFragment.doRequestPermission(this)) {
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
        if (RequestReadContact.onRequestPermissionResult(requestCode, grantResults))
            contactsPresenter.getContacts();
        else
            Toast.makeText(getActivity(), "Второго шанса не будет", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        myRecyclerView = null;
        recyclerViewAdapter = null;
    }


}
