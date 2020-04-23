package com.example.tourguideapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Places {

    @SerializedName("places")
    private List<Place> placeList = null;

    /**
     * Getters & Setters
     */

    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }
}
