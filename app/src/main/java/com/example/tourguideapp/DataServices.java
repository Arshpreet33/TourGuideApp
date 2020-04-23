package com.example.tourguideapp;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DataServices {

    @POST("/login")
    Call<UserDetails> executeLogin(@Body LoginDetails loginDetails);

    @POST("/user/register")
    Call<UserDetails> executeSignUp(@Body LoginDetails loginDetails);

    @POST("/user/edit")
    Call<String> executeEditUserProfile(@Body UserDetails user);

    @GET("/user/edit/{userID}")
    Call<UserDetails> executeGetUserProfileByID(@Path(value = "userID", encoded = true) int userID);

    @GET("/place/search/{strPlace}")
    Call<Places> executeSearchPlaces(@Path(value = "strPlace", encoded = true) String strPlace);

    @GET("/place/{placeID}")
    Call<Place> executeGetPlacesByID(@Path(value = "placeID", encoded = true) int placeID);

    @GET("/place/{cityID}")
    Call<Places> executeGetPlacesByCityID(@Path(value = "cityID", encoded = true) int cityID);



}
