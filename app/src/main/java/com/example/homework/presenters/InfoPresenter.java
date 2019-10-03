package com.example.homework.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.ContactsProvider;
import com.example.homework.views.InfoView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class InfoPresenter extends MvpPresenter<InfoView> {
    private Disposable disposable;
    private String id;
    private ContactsProvider contactsProvider;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onRequestPermission();
    }

    public InfoPresenter(String id, ContactsProvider contactsProvider) {
        this.id = id;
        this.contactsProvider = contactsProvider;
    }

    public void init() {
        disposable = contactsProvider.getContactSingle(id)
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
        contactsProvider = null;
    }
}
