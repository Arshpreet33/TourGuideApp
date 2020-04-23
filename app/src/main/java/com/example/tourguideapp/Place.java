package com.example.tourguideapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Place implements Parcelable {

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

    Place() {

    }

    public Place(int placeID, int cityID, String name, String description, String location, String imageURL) {
        this.placeID = placeID;
        this.cityID = cityID;
        this.name = name;
        this.description = description;
        this.location = location;
        this.imageURL = imageURL;
    }

    protected Place(Parcel in) {
        placeID = in.readInt();
        cityID = in.readInt();
        name = in.readString();
        description = in.readString();
        location = in.readString();
        imageURL = in.readString();
        status = in.readByte() != 0;
        str = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(placeID);
        dest.writeInt(cityID);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(imageURL);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(str);
    }
}
