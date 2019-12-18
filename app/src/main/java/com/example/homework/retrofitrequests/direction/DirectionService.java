package com.example.homework.retrofitrequests.direction;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

import io.reactivex.Single;

public class DirectionService {
    private GoogleDirectionApi googleDirectionApi;
    private String googleApiKey;

    public DirectionService(GoogleDirectionApi googleDirectionApi, String googleApiKey) {
        this.googleDirectionApi = googleDirectionApi;
        this.googleApiKey = googleApiKey;
    }

    public Single<PolylineOptions> getPolyline(LatLng origin, LatLng direction) {

        return googleDirectionApi.getPolyline(origin.latitude + "," + origin.longitude
                , direction.latitude + "," + direction.longitude
                , googleApiKey)
                .map(directionResponse -> {
                    if (directionResponse.getDirectionStatus().equals("OK")) {
                        PolylineOptions polylineOptions = new PolylineOptions();
                        List<LatLng> polyLine = PolyUtil.decode(directionResponse.getRouteList().get(0).getOverViewPolyline().getPolyline());
                        for (int i = 0; i < polyLine.size(); i++) {
                            polylineOptions.add(polyLine.get(i));
                        }
                        return polylineOptions;
                    } else
                        return null;
                });
    }
}
