package com.example.getdirecttolocation.Services;

import com.example.getdirecttolocation.RLocation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Anh Tu Vo on 05/29/2016.
 */
public interface APIService {
        @GET("maps/api/geocode/json?")
        Call<RLocation> getLocation(@Query("address") String address, @Query("sensor") String sensor);
}
