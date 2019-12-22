package com.example.homework.presentation.presenters;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.domain.interactor.ContactsInteractor;
import com.example.homework.presentation.views.ContactsView;
import com.example.homework.request.RequestReadContact;
import com.example.homework.schedulers.SchedulerManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private CompositeDisposable compositeDisposable;
    private RequestReadContact requestReadContact;
    private Disposable disposableSearch;
    private final ContactsInteractor contactsInteractor;
    private final SchedulerManager schedulerManager;

    @Inject
    public ContactsPresenter(@NonNull ContactsInteractor contactsInteractor, SchedulerManager schedulerManager) {
        this.contactsInteractor = contactsInteractor;
        this.schedulerManager = schedulerManager;
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
            if (searchText != null && !searchText.equals("")) {
                if (disposableSearch != null) disposableSearch.dispose();
                disposableSearch = contactsInteractor.getContacts(searchText)
                        .subscribeOn(schedulerManager.ioThread())
                        .observeOn(schedulerManager.mainThread())
                        .subscribe(list -> getViewState().setContacts(list)
                                , __ -> getViewState().onError("Cant find contact with this name"));
            } else {
                disposableSearch = contactsInteractor.getContacts("")
                        .subscribeOn(schedulerManager.ioThread())
                        .observeOn(schedulerManager.mainThread())
                        .doOnSubscribe(__ -> getViewState().showLoading())
                        .doOnSuccess(__ -> getViewState().hideLoading())
                        .doOnDispose(() -> getViewState().hideLoading())
                        .subscribe(list -> getViewState().setContacts(list)
                                , __ -> getViewState().onError("Cant find contacts"));

            }
        else {
            getViewState().onError("Please, give read permission");
            getViewState().onRequestPermission(requestReadContact);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableSearch != null) disposableSearch.dispose();
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    void setRequestReadContact(RequestReadContact requestReadContact) {
        this.requestReadContact = requestReadContact;
    }
}
