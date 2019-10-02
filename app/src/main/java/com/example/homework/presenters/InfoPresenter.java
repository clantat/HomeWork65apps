package com.example.homework.presenters;

import android.graphics.Bitmap;

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
    public InfoPresenter(String id, ContactsProvider contactsProvider) {
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
    }
}
