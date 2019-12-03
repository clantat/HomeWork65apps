package com.example.homework.di.modules;

import com.example.homework.di.scopes.MapScreenScope;
import com.example.homework.retrofit.YandexGeoCodeApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class GeoCodeNetworkModule {
    private static final String BASE_URL = "https://geocode-maps.yandex.ru/1.x/?apikey=9c5af65b-31b0-4563-8e22-ba4ea81c7494&sco=latlong&format=json";
    private Retrofit retrofit;

    public GeoCodeNetworkModule() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @MapScreenScope
    YandexGeoCodeApi getYandexGeoCodeApi(){
        return retrofit.create(YandexGeoCodeApi.class);
    }
}
