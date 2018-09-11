package com.nenton.testmagnitapp.data.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResultParse {
    @SerializedName("citiesFrom")
    @Expose
    private List<Cities> citiesFrom = null;
    @SerializedName("citiesTo")
    @Expose
    private List<Cities> citiesTo = null;

    public List<Cities> getCitiesFrom() {
        return citiesFrom;
    }

    public List<Cities> getCitiesTo() {
        return citiesTo;
    }

    public List<Station> getAllStationsFrom(){
        List<Station> stations = new ArrayList<>();
        for (Cities city : citiesFrom) {
            stations.addAll(city.getStations());
        }
        return stations;
    }

    public List<Station> getAllStationsTo(){
        List<Station> stations = new ArrayList<>();
        for (Cities city : citiesTo) {
            stations.addAll(city.getStations());
        }
        return stations;
    }
}
