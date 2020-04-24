package com.example.tourguideapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataServices {

    @POST("/login")
    Call<UserDetails> executeLogin(@Body LoginDetails loginDetails);

    @POST("/user/register")
    Call<UserDetails> executeSignUp(@Body LoginDetails loginDetails);

    @POST("/user/edit")
    Call<String> executeEditUserProfile(@Body UserDetails user);

    @GET("/user/edit/{userID}")
    Call<UserDetails> executeGetUserProfileByID(@Path(value = "userID", encoded = true) int userID);

    @POST("/user/password")
    Call<ChangePassword> executeEditUserPassword(@Body ChangePassword changePassword);

    @POST("/place/search")
    Call<Places> executeSearchPlaces(@Body Place place);

//    @GET("/place/search/{strPlace}/{cityID}")
//    Call<Places> executeSearchPlaces(@Path(value = "strPlace", encoded = true) String strPlace,
//                                     @Path(value = "cityID", encoded = true) int cityID);
//    @GET("/place/search")
//    Call<Places> executeSearchPlaces(
//            @Query("str") String strPlace,
//            @Query("city") int cityID
//    );
    @GET("/place/{placeID}")
    Call<Place> executeGetPlacesByID(@Path(value = "placeID", encoded = true) int placeID);

    @GET("/place/city/{cityID}")
    Call<Places> executeGetPlacesByCityID(@Path(value = "cityID", encoded = true) int cityID);


}
