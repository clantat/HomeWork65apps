package com.example.homework.presentation.presenters;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.domain.interactor.ContactsInteractor;
import com.example.homework.presentation.views.ContactsView;
import com.example.homework.request.RequestReadContact;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private CompositeDisposable compositeDisposable;
    private final RequestReadContact requestReadContact;
    private Disposable disposableSearch;
    private final ContactsInteractor contactsInteractor;

    @Inject
    public ContactsPresenter(@NonNull ContactsInteractor contactsInteractor) {
        this.contactsInteractor = contactsInteractor;
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

    public void getContacts(String searchText) {
        if (requestReadContact.getReadContactPermission())
            if (!TextUtils.isEmpty(searchText)) {
                if (disposableSearch != null) disposableSearch.dispose();
                disposableSearch = contactsInteractor.getContacts(searchText)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(__ -> getViewState().setContacts(__));
            } else {
                disposableSearch = contactsInteractor.getContacts("")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> getViewState().showLoading())
                        .doOnSuccess(__ -> getViewState().hideLoading())
                        .doOnDispose(() -> getViewState().hideLoading())
                        .subscribe(__ -> getViewState().setContacts(__));
            }
        else getViewState().onRequestPermission(requestReadContact);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableSearch != null) disposableSearch.dispose();
        if (compositeDisposable != null) compositeDisposable.dispose();
    }
}
