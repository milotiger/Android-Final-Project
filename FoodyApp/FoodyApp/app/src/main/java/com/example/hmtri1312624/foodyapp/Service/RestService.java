package com.example.hmtri1312624.foodyapp.Service;

import com.example.hmtri1312624.foodyapp.Interface.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by M-Tae on 5/22/2016.
 */

public class RestService {
    private static final String URL = "http://whattoeattoday.apphb.com/api/";
    private Retrofit restAdapter;
    private APIService apiService;

    public RestService()
    {

        restAdapter = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .build();

        //Create an instance of the Service interface that we created from the Buider above
        apiService = restAdapter.create(APIService.class);
    }

    //Return an instance above
    public APIService getService()
    {
        return apiService;
    }
}
