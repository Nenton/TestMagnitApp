package com.nenton.testmagnitapp.data.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cities {
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
    @SerializedName("stations")
    @Expose
    private List<Station> stations = null;

    @Override
    public String toString() {
        return cityTitle + ", " + countryTitle;
    }

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

    public List<Station> getStations() {
        return stations;
    }
}
