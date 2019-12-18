package com.example.homework.domain.interactor;

import com.example.homework.data.room.MapContact;
import com.example.homework.domain.repository.MapRepository;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MapInteractorImpl implements MapInteractor {
    private MapRepository mapRepository;

    public MapInteractorImpl(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    @Override
    public Single<MapContact> getMapContact(String id) {
        return mapRepository.getMapContact(id);
    }

    @Override
    public Completable setMapContact(String id, LatLng coordination, String address) {
        return mapRepository.setMapContact(id, coordination, address);
    }

    @Override
    public Single<String> getAddress(LatLng coordination) {
        return mapRepository.getAddress(coordination);
    }

    @Override
    public Single<List<MapContact>> getAllMapContact() {
        return mapRepository.getAllMapContact();
    }

    @Override
    public Single<LatLng> getCurrentLocation() {
        return mapRepository.getCurrentLocation();
    }

    @Override
    public Single<PolylineOptions> setDirection(LatLng origin, LatLng direction) {
        return mapRepository.getPolyline(origin,direction);
    }
}
