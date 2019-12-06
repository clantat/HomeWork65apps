package com.example.homework.data.repository;

import com.example.homework.data.provider.MapContactProvider;
import com.example.homework.data.room.MapContact;
import com.example.homework.domain.repository.MapRepository;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MapRepositoryImpl implements MapRepository {
    private MapContactProvider mapContactProvider;

    public MapRepositoryImpl(MapContactProvider mapContactProvider) {
        this.mapContactProvider = mapContactProvider;
    }

    @Override
    public Single<MapContact> getMapContact(String id) {
        return mapContactProvider.getMapContact(id);
    }

    @Override
    public Completable setMapContact(String id, LatLng coordination, String address) {
        return mapContactProvider.addMapContact(id, coordination, address);
    }

    @Override
    public Single<String> getAddress(LatLng coordination) {
        return mapContactProvider.getAddress(coordination);
    }

    @Override
    public Single<List<MapContact>> getAllMapContact() {
        return mapContactProvider.getAllMapContact();
    }

}
