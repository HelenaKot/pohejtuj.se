package com.fancytank.kit.pohejtujse.api.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coordinates implements Parcelable {
    @SerializedName("x")
    public double x;
    @SerializedName("y")
    public double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected Coordinates(Parcel in) {
        x = in.readDouble();
        y = in.readDouble();
    }

    public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {
        @Override
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(x);
        parcel.writeDouble(y);
    }
}
