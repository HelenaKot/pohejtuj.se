package com.fancytank.kit.pohejtujse.api.dto;

import com.google.gson.annotations.SerializedName;

public class Coordinates {
    @SerializedName("x")
    public float x;
    @SerializedName("y")
    public float y;

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
