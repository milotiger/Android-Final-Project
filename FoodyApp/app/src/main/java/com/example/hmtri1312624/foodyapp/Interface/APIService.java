package com.example.hmtri1312624.foodyapp.Interface;

import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.example.hmtri1312624.foodyapp.Model.FoodyMenuItem;
import com.example.hmtri1312624.foodyapp.RLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by M-Tae on 5/22/2016.
 */
public interface APIService {
    @GET("api/search/{foodname}/1") // Default get 1 places
    Call<List<FoodyItemInfo>> GetPlaces(@Path("foodname") String foodname);

    @POST("api/fullsizealbum")
    Call<FoodyItemInfo> GetAlbum(@Body FoodyItemInfo foodyitem);

    @GET("maps/api/geocode/json?")
    Call<RLocation> getLocation(@Query("address") String address, @Query("sensor") String sensor);
}
