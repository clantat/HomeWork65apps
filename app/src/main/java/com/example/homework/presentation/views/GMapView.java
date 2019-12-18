package com.example.homework.presentation.views;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.homework.data.room.MapContact;
import com.example.homework.request.RequestPermissionFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public interface GMapView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void onRequestPermission(RequestPermissionFragment requestPermissionFragment);

    void addCurrentAddressMarker(LatLng latLng, String address);

    void addMarker(LatLng latLng, String title);

    void mapAsync();

    void onError(String msg);

    void currentLocation(LatLng latLng);

    void allMapContact(List<MapContact> coordinationList);

    void setDirection(PolylineOptions polylineOptions);

}
