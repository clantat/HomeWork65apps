package com.example.homework.data.repository;

import com.example.homework.data.provider.MapContactProvider;
import com.example.homework.domain.model.MapContactModel;
import com.example.homework.domain.repository.MapRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MapRepositoryImpl implements MapRepository {
    private MapContactProvider mapContactProvider;

    public MapRepositoryImpl(MapContactProvider mapContactProvider) {
        this.mapContactProvider = mapContactProvider;
    }

    @Override
    public Single<MapContactModel> getMapContact(String id) {
        return mapContactProvider.getMapContact(id);
    }

    @Override
    public Completable setMapContact(String id, String name, String coordination, String address) {
        return mapContactProvider.addMapContact(id, name, coordination, address);
    }

    @Override
    public Single<String> getAddress(String coordination) {
        return mapContactProvider.getAddress(coordination);
    }

    @Override
    public Single<List<MapContactModel>> getAllMapContact() {
        return mapContactProvider.getAllMapContact();
    }

    @Override
    public Single<String> getCurrentLocation() {
        return mapContactProvider.getCurrentLocation();
    }

    @Override
    public Single<List<String>> getPolyline(String origin, String direction) {
        return mapContactProvider.getPolyline(origin, direction);
    }

}
