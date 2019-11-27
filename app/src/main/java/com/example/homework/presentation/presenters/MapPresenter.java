package com.example.homework.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.presentation.views.MapView;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
//        getViewState().onRequestPermission(requestReadContact);
    }

    public MapPresenter() {
    }
}
