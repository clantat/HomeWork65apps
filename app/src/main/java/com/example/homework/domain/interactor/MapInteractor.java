package com.example.homework.domain.interactor;

import com.example.homework.data.room.MapContact;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MapInteractor {
    Single<MapContact> getMapContact(String id);
    Completable setMapContact(String id, LatLng latLng, String address);
    Single<String> getAddress(LatLng coordination);
    Single<List<MapContact>> getAllMapContact();
    LatLng getCurrentLocation();
    //TODO add current location function
}
