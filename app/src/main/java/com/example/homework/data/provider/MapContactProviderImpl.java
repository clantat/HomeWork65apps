package com.example.homework.data.provider;

import com.example.homework.data.room.MapContact;
import com.example.homework.data.room.MapDatabase;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.Single;

public class MapContactProviderImpl implements MapContactProvider {

    private MapDatabase mapDatabase;

    @Inject
    public MapContactProviderImpl(MapDatabase mapDatabase) {
        this.mapDatabase = mapDatabase;

    }

    @Override
    public Single<Boolean> isMapContactCreated(String id) {
        return Single.fromCallable(() -> mapDatabase.mapContactDao().getMapContact(id) != null);
    }

    @Override
    public void addMapContact(String id, LatLng coordination, String address) {
        mapDatabase.mapContactDao().insertMapContact(new MapContact(id, coordination.latitude, coordination.longitude, address));
    }

    @Override
    public void updateMapContact(String id, LatLng coordination, String address) {
        mapDatabase.mapContactDao().updateMapContact(new MapContact(id, coordination.latitude, coordination.longitude, address));
    }

    @Override
    public Single<MapContact> getMapContact(String id) {
        return mapDatabase.mapContactDao().getMapContact(id);
    }
}
