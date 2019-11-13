package com.example.homework.presentation.presenters;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.domain.interactor.ContactsInteractor;
import com.example.homework.presentation.views.ContactsView;
import com.example.homework.request.RequestReadContact;
import com.example.homework.room.AppDatabase;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private final RequestReadContact requestReadContact;
    private Disposable disposableSearch;
    private Disposable disposableDatabase;
    private final ContactsInteractor contactsInteractor;
    private final AppDatabase appDatabase;

    @Inject
    public ContactsPresenter(@NonNull ContactsInteractor contactsInteractor, @NonNull AppDatabase appDatabase) {
        this.contactsInteractor = contactsInteractor;
        this.appDatabase = appDatabase;
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
        if (disposableDatabase == null) {
            disposableDatabase = contactsInteractor.getContacts("")
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(__ -> getViewState().showLoading())
                    .doOnSuccess(__ -> getViewState().hideLoading())
                    .doOnDispose(() -> getViewState().hideLoading())
                    .subscribe(list -> appDatabase.shortContactDao().insertAll(list));
        }
        if (requestReadContact.getReadContactPermission()) {
            if (!TextUtils.isEmpty(searchText)) {
                if (disposableSearch != null) disposableSearch.dispose();
                disposableSearch = appDatabase.shortContactDao().getSearchShortContact(searchText)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> getViewState().setContacts(list));
            } else {
                disposableSearch = appDatabase.shortContactDao().getAllShortContact()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> getViewState().setContacts(list));
            }
        } else getViewState().onRequestPermission(requestReadContact);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableSearch != null) disposableSearch.dispose();
        if (disposableDatabase != null) disposableDatabase.dispose();
        if (appDatabase.isOpen()) appDatabase.close();
    }
}
