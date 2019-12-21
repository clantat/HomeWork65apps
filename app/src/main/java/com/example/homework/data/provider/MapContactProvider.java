package com.example.homework.data.provider;

import com.example.homework.domain.model.MapContactModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MapContactProvider {
    Completable addMapContact(String id, String coordination, String address);

    Single<MapContactModel> getMapContact(String id);

    Single<String> getAddress(String coordination);

    Single<List<MapContactModel>> getAllMapContact();

    Single<String> getCurrentLocation();

    Single<List<String>> getPolyline(String origin, String direction);
}
