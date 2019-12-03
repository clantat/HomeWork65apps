package com.example.homework.data.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MapContact {
    @NonNull
    @PrimaryKey
    private String id;
    private double lat;
    private double lng;
    private String address;

    public MapContact(@NonNull String id, double lat, double lng, String address) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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
