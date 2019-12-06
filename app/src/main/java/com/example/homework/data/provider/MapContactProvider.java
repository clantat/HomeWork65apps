package com.example.homework.data.provider;

import com.example.homework.data.room.MapContact;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MapContactProvider {
    Completable addMapContact(String id, LatLng coordination, String address);
    Single<MapContact> getMapContact(String id);
    Single<String> getAddress(LatLng coordination);
    Single<List<MapContact>> getAllMapContact();
}
