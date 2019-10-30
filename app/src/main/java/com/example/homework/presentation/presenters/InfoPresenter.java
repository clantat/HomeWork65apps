package com.example.homework.presentation.presenters;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.request.RequestReadContact;
import com.example.homework.domain.interactor.InfoInteractor;
import com.example.homework.presentation.views.InfoView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class InfoPresenter extends MvpPresenter<InfoView> {
    private Disposable disposable;
    private String id;
    private RequestReadContact requestReadContact;
    private InfoInteractor infoInteractor;
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onRequestPermission(requestReadContact);
    }

    public RequestReadContact getRequestReadContact() {
        return this.requestReadContact;
    }

    @Inject
    public InfoPresenter(@NonNull InfoInteractor infoInteractor, String id) {
        this.infoInteractor = infoInteractor;
        this.id = id;
        requestReadContact = new RequestReadContact();
    }

    public void init() {
        disposable = infoInteractor.getInfoContact(id)
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
        id = null;
        infoInteractor = null;
    }
}
