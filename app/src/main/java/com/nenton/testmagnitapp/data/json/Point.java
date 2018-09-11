package com.nenton.testmagnitapp.data.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Point implements Serializable{
    @SerializedName("longitude")
    @Expose
    public float longitude;
    @SerializedName("latitude")
    @Expose
    public float latitude;
}
