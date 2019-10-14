package com.example.homework;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.homework.presenters.ContactsPresenter;
import com.example.homework.views.ContactsView;

import java.util.List;

import static android.content.ContentValues.TAG;


public class FragmentContacts extends MvpAppCompatFragment implements ContactsView {
    @InjectPresenter
    ContactsPresenter contactsPresenter;

    private View view;
    private RecyclerView myRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SearchView searchView;
    private CharSequence searchText;

    public FragmentContacts() {
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        searchText = searchView.getQuery();
        outState.putCharSequence("searchText", searchText);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            searchText = savedInstanceState.getCharSequence("searchText");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_fragment, container, false);
        myRecyclerView = view.findViewById(R.id.contact_recyclerview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);
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
        for (int i = 0; i < list.size(); i++) {
            Log.i(TAG, "setContacts: name" + list.get(i).getName());
        }
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
        myRecyclerView.setAdapter(null);
        myRecyclerView = null;
        searchView = null;
        searchText = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contacts_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactsPresenter.getContacts(newText);
                return false;
            }
        });
        if (!TextUtils.isEmpty(searchText)) {
            searchItem.expandActionView();
            searchView.setQuery(searchText, false);
        }

    }
}
