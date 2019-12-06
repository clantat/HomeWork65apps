package com.example.homework.presentation.views;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.homework.request.RequestPermissionFragment;
import com.google.android.gms.maps.model.LatLng;

public interface GMapView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void onRequestPermission(RequestPermissionFragment requestPermissionFragment);
    void addMarker(LatLng latLng);
    void addMarker(LatLng latLng, String title);
    void mapAsync();
    void onError(String msg);

}
