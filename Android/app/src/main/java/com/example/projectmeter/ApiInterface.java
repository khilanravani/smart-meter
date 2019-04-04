package com.example.projectmeter;

import com.example.projectmeter.Models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("record/{meter_id}")
    Call<User> getUserDetails(@Path("meter_id") int meterId, @Query("meter_id") int meter_id);

}
