package com.example.homework.domain.interactor;

import com.example.homework.domain.model.MapContactModel;
import com.example.homework.domain.repository.MapRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MapInteractorImpl implements MapInteractor {
    private MapRepository mapRepository;

    public MapInteractorImpl(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    @Override
    public Single<MapContactModel> getMapContact(String id) {
        return mapRepository.getMapContact(id);
    }

    @Override
    public Completable setMapContact(String id, String coordination, String address) {
        return mapRepository.setMapContact(id, coordination, address);
    }

    @Override
    public Single<String> getAddress(String coordination) {
        return mapRepository.getAddress(coordination);
    }

    @Override
    public Single<List<MapContactModel>> getAllMapContact() {
        return mapRepository.getAllMapContact();
    }

    @Override
    public Single<String> getCurrentLocation() {
        return mapRepository.getCurrentLocation();
    }

    @Override
    public Single<List<String>> setDirection(String origin, String direction) {
        return mapRepository.getPolyline(origin, direction);
    }
}
