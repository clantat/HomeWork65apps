package com.example.homework.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.ContactsProvider;
import com.example.homework.RequestReadContact;
import com.example.homework.views.ContactsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private ContactsProvider contactsProvider;
    private CompositeDisposable compositeDisposable;

    public ContactsPresenter(ContactsProvider contactsProvider) {
        this.contactsProvider = contactsProvider;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onRequestPermission(new RequestReadContact());
    }

    public void getContacts() {
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(contactsProvider.getContacts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> getViewState().setContacts(item)));
    }

    public void getContacts(String searchText) {
        compositeDisposable.add(contactsProvider.getContacts(searchText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> getViewState().setContacts(item)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        contactsProvider = null;
    }
}
