package com.example.homework.domain.model;

public class MapContactModel {

    private String id;
    private double lat;
    private double lng;
    private String address;

    public MapContactModel(String id, double lat, double lng, String address) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
