package com.example.homework.retrofitrequests.direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionResponse {
    @SerializedName("status")
    @Expose
    private String directionStatus;

    @SerializedName("routes")
    @Expose
    private List<Route> routeList;

    public String getDirectionStatus() {
        return directionStatus;
    }

    public void setDirectionStatus(String directionStatus) {
        this.directionStatus = directionStatus;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }
}
