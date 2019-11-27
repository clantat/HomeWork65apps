package com.example.homework.network;

import com.google.gson.annotations.SerializedName;

public class CoordinationModel {
    @SerializedName("id")
    private int id;
    @SerializedName("y")
    private double y;
    @SerializedName("x")
    private double x;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
}
