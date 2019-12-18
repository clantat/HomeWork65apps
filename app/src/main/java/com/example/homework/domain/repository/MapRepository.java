package com.example.homework.domain.repository;

import com.example.homework.data.room.MapContact;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MapRepository {
    Single<MapContact> getMapContact(String id);

    Completable setMapContact(String id, LatLng coordination, String address);

    Single<String> getAddress(LatLng coordination);

    Single<List<MapContact>> getAllMapContact();

    Single<LatLng> getCurrentLocation();

    Single<PolylineOptions> getPolyline(LatLng origin, LatLng direction);

}
