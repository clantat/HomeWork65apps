package com.example.homework.data.provider;

import com.example.homework.data.room.MapContact;
import com.example.homework.data.room.MapDatabase;
import com.example.homework.retrofit.GeoCodingService;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MapContactProviderImpl implements MapContactProvider {

    private MapDatabase mapDatabase;
    private GeoCodingService geoCodingService;
    @Inject
    public MapContactProviderImpl(MapDatabase mapDatabase, GeoCodingService geoCodingService) {
        this.mapDatabase = mapDatabase;
        this.geoCodingService = geoCodingService;

    }

    @Override
    public Completable addMapContact(String id, LatLng coordination, String address) {
        return mapDatabase.mapContactDao().insertMapContact(new MapContact(id, coordination.latitude, coordination.longitude, address));
    }

    @Override
    public Single<MapContact> getMapContact(String id) {
        return mapDatabase.mapContactDao().getMapContact(id);
    }

    @Override
    public Single<String> getAddress(LatLng coordination) {
        return geoCodingService.getAddress(coordination);
    }

    @Override
    public Single<List<MapContact>> getAllMapContact() {
        return mapDatabase.mapContactDao().getAllMapContact();
    }
}
