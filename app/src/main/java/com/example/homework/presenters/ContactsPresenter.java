package com.example.homework.presenters;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.ContactsProvider;
import com.example.homework.RequestReadContact;
import com.example.homework.views.ContactsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private ContactsProvider contactsProvider;
    private CompositeDisposable compositeDisposable;
    private RequestReadContact requestReadContact;
    private Disposable disposableSearch;

    public ContactsPresenter(ContactsProvider contactsProvider) {
        this.contactsProvider = contactsProvider;
        compositeDisposable = new CompositeDisposable();
        requestReadContact = new RequestReadContact();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onRequestPermission(requestReadContact);
    }


    public RequestReadContact getRequestReadContact() {
        return this.requestReadContact;
    }

    public void getContacts() {
        if (requestReadContact.getReadContactPermission())
            compositeDisposable.add(contactsProvider.getContacts()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(__ -> getViewState().showLoading())
                    .doOnSuccess(__ -> getViewState().hideLoading())
                    .subscribe(__ -> getViewState().setContacts(__)
                    ));
        else getViewState().onRequestPermission(requestReadContact);
    }

    public void getContacts(String searchText) {
        if (requestReadContact.getReadContactPermission())
            if (!TextUtils.isEmpty(searchText)) {
                disposableSearch = contactsProvider.getContacts(searchText)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doAfterSuccess(__ -> disposableSearch.dispose())
                        .subscribe(__ -> getViewState().setContacts(__));
            } else getContacts();
        else getViewState().onRequestPermission(requestReadContact);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableSearch != null) disposableSearch.dispose();
        if (compositeDisposable != null) compositeDisposable.dispose();
        contactsProvider = null;
    }
}
