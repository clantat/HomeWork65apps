package com.example.homework.data.provider;

import android.content.Context;

import com.example.homework.data.room.MapContact;
import com.example.homework.data.room.MapDatabase;
import com.example.homework.retrofitrequests.direction.DirectionService;
import com.example.homework.retrofitrequests.geocoding.GeoCodingService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MapContactProviderImpl implements MapContactProvider {

    private MapDatabase mapDatabase;
    private GeoCodingService geoCodingService;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private DirectionService directionService;


    @Inject
    public MapContactProviderImpl(MapDatabase mapDatabase, GeoCodingService geoCodingService, Context context , DirectionService directionService) {
        this.mapDatabase = mapDatabase;
        this.geoCodingService = geoCodingService;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.directionService = directionService;
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

    @Override
    public Single<LatLng> getCurrentLocation() {
        return Single.create(e ->
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        e.onSuccess(new LatLng(location.getLatitude(),
                                location.getLongitude()));
                    } else
                        e.onError(new Throwable("Current location is null"));
                }));
    }

    @Override
    public Single<PolylineOptions> getPolyline(LatLng origin, LatLng direction) {
        return directionService.getPolyline(origin,direction);
    }
}
