package com.example.homework.retrofitrequests.direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverViewPolyline {
    @SerializedName("points")
    @Expose
    private String polyline;

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }
}
