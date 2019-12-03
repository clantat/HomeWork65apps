package com.example.homework.data.provider;

import com.example.homework.data.room.MapContact;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Single;

public interface MapContactProvider {
    Single<Boolean> isMapContactCreated(String id);
    void addMapContact(String id, LatLng coordination,String address);
    void updateMapContact(String id, LatLng coordination,String address);
    Single<MapContact> getMapContact(String id);
}
