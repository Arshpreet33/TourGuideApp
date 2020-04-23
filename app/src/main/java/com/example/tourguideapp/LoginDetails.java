package com.example.tourguideapp;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LoginDetails {

    @SerializedName("loginID")
    private String loginID;

    @SerializedName("user")
    private UserDetails user;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("lastLoginTime")
    private Date lastLoginTime;

    @SerializedName("status")
    private boolean status;

    public LoginDetails() {

    }

    public LoginDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Getters and Setters
     */

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
