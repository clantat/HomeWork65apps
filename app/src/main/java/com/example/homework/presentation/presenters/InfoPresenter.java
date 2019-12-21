package com.example.homework.presentation.presenters;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.core.Screens;
import com.example.homework.domain.interactor.InfoInteractor;
import com.example.homework.presentation.fragment.MapFragment;
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
    private final RequestReadContact requestReadContact;
    private final InfoInteractor infoInteractor;
    private final SchedulerManager schedulerManager;
    private final Router router;
    private final int screenId;

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
                         SchedulerManager schedulerManager, Router router, int screenId) {
        this.infoInteractor = infoInteractor;
        this.id = id;
        this.schedulerManager = schedulerManager;
        this.router = router;
        this.screenId = screenId;
        requestReadContact = new RequestReadContact();
    }

    public void init() {
        disposable = infoInteractor.getInfoContact(id)
                .subscribeOn(schedulerManager.ioThread())
                .observeOn(schedulerManager.mainThread())
                .subscribe(item -> getViewState().showInfo(item));
    }
    public void clickMapButton(){
        router.navigateTo(new Screens.MapScreen(screenId+1,id));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
