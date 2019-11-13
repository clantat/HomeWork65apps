package com.example.homework.presentation.presenters;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.domain.interactor.InfoInteractor;
import com.example.homework.presentation.views.InfoView;
import com.example.homework.request.RequestReadContact;
import com.example.homework.room.AppDatabase;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class InfoPresenter extends MvpPresenter<InfoView> {
    private Disposable disposable;
    private final String id;
    private final RequestReadContact requestReadContact;
    private final InfoInteractor infoInteractor;
    private final AppDatabase appDatabase;
    private Disposable disposableInfo;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onRequestPermission(requestReadContact);
    }

    public RequestReadContact getRequestReadContact() {
        return this.requestReadContact;
    }

    @Inject
    public InfoPresenter(@NonNull AppDatabase appDatabase, @NonNull InfoInteractor infoInteractor, String id) {
        this.appDatabase = appDatabase;
        this.infoInteractor = infoInteractor;
        this.id = id;
        requestReadContact = new RequestReadContact();
    }

    public void init() {

        disposableInfo = infoInteractor.getInfoContact(id)
                .subscribeOn(Schedulers.io())
                .subscribe(item -> appDatabase.contactDao().insertContact(item));

        disposable = appDatabase.contactDao().getContact(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> getViewState().showInfo(item));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
        if (disposableInfo != null) {
            disposableInfo.dispose();
        }
    }
}
