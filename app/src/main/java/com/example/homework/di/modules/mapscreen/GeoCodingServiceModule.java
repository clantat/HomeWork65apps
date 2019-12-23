package com.example.homework.di.modules.mapscreen;

import android.content.Context;

import com.example.homework.R;
import com.example.homework.di.scopes.MapScreenScope;
import com.example.homework.retrofitrequests.geocoding.GeoCodingService;
import com.example.homework.retrofitrequests.geocoding.YandexGeoCodeApi;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class GeoCodingServiceModule {
    private static final String BASE_URL = "https://geocode-maps.yandex.ru";
    private Retrofit retrofit;

    public GeoCodingServiceModule() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client.build())
                .build();
    }

    @Provides
    @MapScreenScope
    GeoCodingService getGeoCodingService(Context context) {
        return new GeoCodingService(retrofit.create(YandexGeoCodeApi.class)
                ,context.getResources().getString(R.string.yandex_geocode_api_key)
                ,context.getResources().getString(R.string.format_json)
                ,context.getResources().getString(R.string.sco_latlong)
                );
    }
}
