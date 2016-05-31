package com.example.getdirecttolocation.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anh Tu Vo on 05/29/2016.
 */
public class RestService {
    static Retrofit retrofit = null;

    public static Retrofit getService()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://maps.google.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
