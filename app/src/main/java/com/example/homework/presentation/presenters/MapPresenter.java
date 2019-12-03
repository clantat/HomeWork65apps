package com.example.homework.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.presentation.views.GMapView;
import com.example.homework.request.RequestAccessLocation;
import com.google.android.gms.maps.model.LatLng;

@InjectViewState
public class MapPresenter extends MvpPresenter<GMapView> {
    private RequestAccessLocation requestAccessLocation;

    public MapPresenter() {
        this.requestAccessLocation = new RequestAccessLocation();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onRequestPermission(requestAccessLocation);
    }

    public void mapCreated() {
        if (requestAccessLocation.getLocationPermission()) {
            getViewState().mapAsync();
        }
    }

    public LatLng startLocation() {
        return new LatLng(56.8497581, 53.2044792);

    }

    public RequestAccessLocation getRequestAccessLocation() {
        return this.requestAccessLocation;
    }
}
