package com.example.homework.domain.interactor;

import com.example.homework.domain.model.MapContactModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MapInteractor {
    Single<MapContactModel> getMapContact(String id);

    Completable setMapContact(String id, String name, String latLng, String address);

    Single<String> getAddress(String coordination);

    Single<List<MapContactModel>> getAllMapContact();

    Single<String> getCurrentLocation();

    Single<List<String>> setDirection(String origin, String direction);
}
