package com.example.hmtri1312624.foodyapp.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by M-Tae on 6/17/2016.
 */
public class RestGoogleService {
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
