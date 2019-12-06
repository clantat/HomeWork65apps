package com.example.homework.retrofit;

import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Single;

public class GeoCodingService {
    private YandexGeoCodeApi yandexGeoCodeApi;
    private String apiKey;
    private String format; //TODO добавить в strings format, sco

    public GeoCodingService(YandexGeoCodeApi yandexGeoCodeApi, String apiKey) {
        this.yandexGeoCodeApi = yandexGeoCodeApi;
        this.apiKey = apiKey;
    }

    public Single<String> getAddress(LatLng latLnd) {
        return yandexGeoCodeApi.getGeoCodePost(apiKey, "json", "latlong", latLnd.latitude + "," + latLnd.longitude)
                .map(reverseGeoCoding -> reverseGeoCoding
                        .getResponse()
                        .getGeoObjectCollection()
                        .getFeatureMemberList()
                        .get(0)
                        .getGeoObject()
                        .getAddress());
    }
}
