package com.example.homework.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.ContactsProvider;
import com.example.homework.views.ContactsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private ContactsProvider contactsProvider;
    private Disposable disposable;

    public ContactsPresenter(ContactsProvider contactsProvider) {
        this.contactsProvider = contactsProvider;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onRequestPermission();
    }

    public void getContacts() {
        disposable = contactsProvider.getContacts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> getViewState().setContacts(item));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
