package com.example.homework.retrofit;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexGeoCodeApi {
    @GET("/1.x/")
    Single<GeoCodingResponse> getGeoCodePost(
            @Query("apikey") String apiKey
            , @Query("format") String format
            , @Query("sco") String sco
            , @Query("geocode") String coordination);
}
