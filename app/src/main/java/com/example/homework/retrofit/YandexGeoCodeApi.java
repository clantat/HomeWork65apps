package com.example.homework.retrofit;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface YandexGeoCodeApi {
    @GET("&geocode={lat},{lng}")
    Single<GeoCodingResponse> getGeoCodePost(@Path("lat") double lat, @Path("lng") double lng);
}
