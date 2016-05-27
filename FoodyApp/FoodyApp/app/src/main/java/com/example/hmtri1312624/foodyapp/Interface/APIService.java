package com.example.hmtri1312624.foodyapp.Interface;

import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by M-Tae on 5/22/2016.
 */
public interface APIService {
    @GET("result/{foodname}/1") // Default get 10 places
    Call<List<FoodyItemInfo>> GetPlaces(@Path("foodname") String foodname);
}
