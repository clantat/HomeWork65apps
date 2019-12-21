package com.example.homework.retrofitrequests.direction;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class DirectionService {
    private GoogleDirectionApi googleDirectionApi;
    private String googleApiKey;
    private static final String VALID_DIRECTION_STATUS = "OK";

    public DirectionService(GoogleDirectionApi googleDirectionApi, String googleApiKey) {
        this.googleDirectionApi = googleDirectionApi;
        this.googleApiKey = googleApiKey;
    }

    public Single<List<String>> getPolyline(LatLng origin, LatLng direction) {

        return googleDirectionApi.getPolyline(origin.latitude + "," + origin.longitude
                , direction.latitude + "," + direction.longitude
                , googleApiKey)
                .map(directionResponse -> {
                    if (directionResponse.getDirectionStatus().equals(VALID_DIRECTION_STATUS)) {
                        List<String> stringPolyLine = new ArrayList<>();
                        List<LatLng> polyLine = PolyUtil.decode(directionResponse.getRouteList().get(0).getOverViewPolyline().getPolyline());
                        for (int i = 0; i < polyLine.size(); i++) {
                            stringPolyLine.add(polyLine.get(i).toString());
                        }
                        return stringPolyLine;
                    } else
                        return null;
                });
    }
}
