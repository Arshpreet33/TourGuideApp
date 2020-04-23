package com.example.tourguideapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataServices {

    @POST("/login")
    Call<String> executeLogin(@Body LoginDetails loginDetails);

    @POST("/user/register")
    Call<String> executeSignUp(@Body LoginDetails loginDetails);
}
