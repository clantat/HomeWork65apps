package com.example.homework.retrofitrequests.geocoding;

import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Single;

public class GeoCodingService {
    private YandexGeoCodeApi yandexGeoCodeApi;
    private String apiKey;
    private String format;
    private String sco;

    public GeoCodingService(YandexGeoCodeApi yandexGeoCodeApi, String apiKey, String format, String sco) {
        this.yandexGeoCodeApi = yandexGeoCodeApi;
        this.apiKey = apiKey;
        this.format = format;
        this.sco = sco;
    }

    public Single<String> getAddress(LatLng latLnd) {
        return yandexGeoCodeApi.getGeoCodePost(apiKey, format,sco, latLnd.latitude + "," + latLnd.longitude)
                .map(reverseGeoCoding -> reverseGeoCoding
                        .getResponse()
                        .getGeoObjectCollection()
                        .getFeatureMemberList()
                        .get(0)
                        .getGeoObject()
                        .getAddress());
    }
}
