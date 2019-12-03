package com.example.homework.retrofit;

import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Single;

public class GeoCodingService {
    private YandexGeoCodeApi yandexGeoCodeApi;

    public GeoCodingService(YandexGeoCodeApi yandexGeoCodeApi) {
        this.yandexGeoCodeApi = yandexGeoCodeApi;
    }

    public Single<String> getAddress(LatLng coordination) {
        return yandexGeoCodeApi.getGeoCodePost(coordination.latitude, coordination.longitude)
                .map(reverseGeoCoding -> reverseGeoCoding
                        .getResponse()
                        .getGeoObjectCollection()
                        .getFeatureMemberList()
                        .get(0)
                        .getGeoObjectList()
                        .get(0)
                        .getAddress());
    }
}
