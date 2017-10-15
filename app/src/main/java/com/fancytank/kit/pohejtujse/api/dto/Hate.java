package com.fancytank.kit.pohejtujse.api.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Hate implements Parcelable { //todo sorry

    @SerializedName("local")
    public Coordinates coordinates;

    @SerializedName("photos")
    public String[] images; //base64

    @SerializedName("text")
    public String text;

    public Hate() {

    }

    protected Hate(Parcel in) {
        coordinates = in.readParcelable(Coordinates.class.getClassLoader());
        images = in.createStringArray();
        text = in.readString();
    }

    public static final Creator<Hate> CREATOR = new Creator<Hate>() {
        @Override
        public Hate createFromParcel(Parcel in) {
            return new Hate(in);
        }

        @Override
        public Hate[] newArray(int size) {
            return new Hate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(coordinates, i);
        parcel.writeStringArray(images);
        parcel.writeString(text);
    }
}
