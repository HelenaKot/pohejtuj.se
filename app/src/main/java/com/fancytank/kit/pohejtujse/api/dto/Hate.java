package com.fancytank.kit.pohejtujse.api.dto;

import com.google.gson.annotations.SerializedName;

public class Hate {
    @SerializedName("local")
    public Coordinates coordinates;

    @SerializedName("photos")
    public String[] images; //base64

    @SerializedName("text")
    public String text;
}
