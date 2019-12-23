package com.example.homework.retrofitrequests.direction;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleDirectionApi {
    @GET("/maps/api/directions/json")
    Single<DirectionResponse> getPolyline(
            @Query("origin") String origin
            , @Query("destination") String destination
            , @Query("key") String googleApiKey
    );
}
