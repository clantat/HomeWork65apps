package com.example.homework.presenters;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.ContactsProvider;
import com.example.homework.RequestReadContact;
import com.example.homework.ShortContact;
import com.example.homework.views.ContactsView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private ContactsProvider contactsProvider;
    private CompositeDisposable compositeDisposable;
    private List<ShortContact> contacts;

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
                .doOnSubscribe(__ -> getViewState().showLoading())
                .doOnTerminate(() -> getViewState().hideLoading())
                .subscribe(item -> {
                            getViewState().setContacts(item);
                            contacts = item;
                        }
                ));
    }

    public void getContacts(String searchText) {
        if (!TextUtils.isEmpty(searchText))
            compositeDisposable.add(contactsProvider.getContacts(searchText)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(item -> getViewState().setContacts(item)));
        else getViewState().setContacts(contacts);
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
