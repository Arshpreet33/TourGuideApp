package com.example.tourguideapp;

import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("placeID")
    private int placeID;

    @SerializedName("cityID")
    private int cityID;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("location")
    private String location;

    @SerializedName("imageURL")
    private String imageURL;

    @SerializedName("status")
    private boolean status;

    @SerializedName("str")
    private String str;

    Place(){

    }

    public Place(int placeID, int cityID, String name, String description, String location, String imageURL) {
        this.placeID = placeID;
        this.cityID = cityID;
        this.name = name;
        this.description = description;
        this.location = location;
        this.imageURL = imageURL;
    }

    /**
     * Getters and Setters
     */

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
