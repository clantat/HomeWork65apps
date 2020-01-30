package com.example.homework.data.provider;

import android.content.Context;

import com.example.homework.exception.CurLocException;
import com.example.homework.data.room.MapContact;
import com.example.homework.data.room.MapDatabase;
import com.example.homework.domain.model.MapContactModel;
import com.example.homework.retrofitrequests.direction.DirectionService;
import com.example.homework.retrofitrequests.geocoding.GeoCodingService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MapContactProviderImpl implements MapContactProvider {

    private static final int LENGTH_OF_STRING_LATLNG_EXCESS = 10;
    private MapDatabase mapDatabase;
    private GeoCodingService geoCodingService;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private DirectionService directionService;


    @Inject
    public MapContactProviderImpl(MapDatabase mapDatabase, GeoCodingService geoCodingService, Context context, DirectionService directionService) {
        this.mapDatabase = mapDatabase;
        this.geoCodingService = geoCodingService;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.directionService = directionService;
    }

    @Override
    public Completable addMapContact(String id, String coordination, String address) {
        LatLng coordinationLatLng = getLatLngFromString(coordination);
        return mapDatabase.mapContactDao().insertMapContact(new MapContact(id, coordinationLatLng.latitude, coordinationLatLng.longitude, address));
    }

    @Override
    public Single<MapContactModel> getMapContact(String id) {
        return mapDatabase.mapContactDao().getMapContact(id)
                .map(this::getModelFromMapContact);
    }

    @Override
    public Single<String> getAddress(String coordination) {
        LatLng coordinationLatLng = getLatLngFromString(coordination);
        return geoCodingService.getAddress(coordinationLatLng);
    }

    @Override
    public Single<List<MapContactModel>> getAllMapContact() {
        return mapDatabase.mapContactDao().getAllMapContact()
                .map(mapContacts -> {
                    List<MapContactModel> mcModelList = new ArrayList<>();
                    for (int i = 0; i < mapContacts.size(); i++) {
                        mcModelList.add(getModelFromMapContact(mapContacts.get(i)));
                    }
                    return mcModelList;
                });
    }

    @Override
    public Single<String> getCurrentLocation() {
        return Single.create(e ->
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        e.onSuccess(new LatLng(location.getLatitude(),
                                location.getLongitude()).toString());
                    } else
                        e.onError(new CurLocException("Current location is null"));
                }));
    }

    @Override
    public Single<List<String>> getPolyline(String origin, String direction) {
        LatLng originLatLng = getLatLngFromString(origin);
        LatLng directionLatLng = getLatLngFromString(direction);
        return directionService.getPolyline(originLatLng, directionLatLng);
    }

    private LatLng getLatLngFromString(String latLng) {
        StringBuilder stringBuilder = new StringBuilder(latLng);
        stringBuilder.delete(latLng.length()-1,latLng.length());
        stringBuilder.delete(0,LENGTH_OF_STRING_LATLNG_EXCESS);
        String[] stringLatLng = stringBuilder.toString().split(",");
        return new LatLng(Double.parseDouble(stringLatLng[0]), Double.parseDouble(stringLatLng[1]));
    }

    private MapContactModel getModelFromMapContact(MapContact mapContact) {
        return new MapContactModel(mapContact.getId(), mapContact.getLat(), mapContact.getLng(), mapContact.getAddress());
    }
}
