package com.example.homework.presentation.presenters;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.core.Screens;
import com.example.homework.domain.interactor.InfoInteractor;
import com.example.homework.presentation.views.InfoView;
import com.example.homework.request.RequestReadContact;
import com.example.homework.schedulers.SchedulerManager;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class InfoPresenter extends MvpPresenter<InfoView> {
    private Disposable disposable;
    private final String id;
    private String name;
    private RequestReadContact requestReadContact;
    private final InfoInteractor infoInteractor;
    private final SchedulerManager schedulerManager;
    private final Router router;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onRequestPermission(requestReadContact);
    }

    public RequestReadContact getRequestReadContact() {
        return this.requestReadContact;
    }

    @Inject
    public InfoPresenter(@NonNull InfoInteractor infoInteractor, String id,
                         SchedulerManager schedulerManager, Router router) {
        this.infoInteractor = infoInteractor;
        this.id = id;
        this.schedulerManager = schedulerManager;
        this.router = router;
        this.name = "Chosen contact";
        requestReadContact = new RequestReadContact();
    }

    public void init() {
        if (requestReadContact.getReadContactPermission())
            disposable = infoInteractor.getInfoContact(id)
                    .subscribeOn(schedulerManager.ioThread())
                    .observeOn(schedulerManager.mainThread())
                    .subscribe(item -> {
                        name = item.getName();
                        getViewState().showInfo(item);
                    }, __ -> getViewState().onError("Cant get a contact info"));
        else {
            getViewState().onRequestPermission(requestReadContact);
            getViewState().onError("Please, give read permission");
        }
    }

    public void clickMapButton() {
        router.navigateTo(new Screens.MapScreen(id, name));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    void setRequestReadContact(RequestReadContact requestReadContact) {
        this.requestReadContact = requestReadContact;
    }
}
