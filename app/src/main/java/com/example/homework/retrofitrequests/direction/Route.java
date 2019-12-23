package com.example.homework.retrofitrequests.direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Route {

    @SerializedName("overview_polyline")
    @Expose
    private OverViewPolyline overViewPolyline;

    public OverViewPolyline getOverViewPolyline() {
        return overViewPolyline;
    }

    public void setOverViewPolyline(OverViewPolyline overViewPolyline) {
        this.overViewPolyline = overViewPolyline;
    }
}
