package com.fancytank.kit.pohejtujse.api.dto;

import com.google.gson.annotations.SerializedName;

public class Coordinates {
    @SerializedName("x")
    public double x;
    @SerializedName("y")
    public double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
