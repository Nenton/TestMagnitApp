package com.nenton.testmagnitapp.data.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Station implements Serializable {
    @SerializedName("countryTitle")
    @Expose
    private String countryTitle;
    @SerializedName("point")
    @Expose
    private Point point;
    @SerializedName("districtTitle")
    @Expose
    private String districtTitle;
    @SerializedName("cityId")
    @Expose
    private int cityId;
    @SerializedName("cityTitle")
    @Expose
    private String cityTitle;
    @SerializedName("regionTitle")
    @Expose
    private String regionTitle;
    @SerializedName("stationId")
    @Expose
    private int stationId;
    @SerializedName("stationTitle")
    @Expose
    private String stationTitle;

    public String getCountryTitle() {
        return countryTitle;
    }

    public Point getPoint() {
        return point;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public String getRegionTitle() {
        return regionTitle;
    }

    public int getStationId() {
        return stationId;
    }

    public String getStationTitle() {
        return stationTitle;
    }

    @Override
    public String toString() {
        return stationTitle + ", " + cityTitle;
    }
}
