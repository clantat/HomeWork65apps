package com.example.homework.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeatureMember {
    @SerializedName("featureMember")
    @Expose
    private List<GeoObject> geoObjectList;

    public List<GeoObject> getGeoObjectList() {
        return geoObjectList;
    }

    public void setGeoObjectList(List<GeoObject> geoObjectList) {
        this.geoObjectList = geoObjectList;
    }
}
