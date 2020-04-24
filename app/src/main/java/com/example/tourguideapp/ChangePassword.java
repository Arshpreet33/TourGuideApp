package com.example.tourguideapp;

import com.google.gson.annotations.SerializedName;

public class ChangePassword {

    @SerializedName("oldPassword")
    private String currentPassword;

    @SerializedName("newPassword")
    private String newPassword;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private String data;

    @SerializedName("userID")
    private int userID;

    ChangePassword() {

    }

    public ChangePassword(String currentPassword, String newPassword, String message, String data, int userID) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.message = message;
        this.data = data;
        this.userID = userID;
    }

    /**
     * Getters and Setters
     */

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
