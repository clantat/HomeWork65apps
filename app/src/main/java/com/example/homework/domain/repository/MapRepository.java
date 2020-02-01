package com.example.homework.domain.repository;

import com.example.homework.domain.model.MapContactModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MapRepository {
    Single<MapContactModel> getMapContact(String id);

    Completable setMapContact(String id, String name, String coordination, String address);

    Single<String> getAddress(String coordination);

    Single<List<MapContactModel>> getAllMapContact();

    Single<String> getCurrentLocation();

    Single<List<String>> getPolyline(String origin, String direction);

}
