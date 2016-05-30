package com.example.hmtri1312624.foodyapp.Service;

import com.example.hmtri1312624.foodyapp.Interface.APIService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by M-Tae on 5/22/2016.
 */

public class RestService {
    private static final String URL = "http://whattoeattoday.gear.host/";
    private Retrofit restAdapter;
    private APIService apiService;

    public RestService()
    {

        final OkHttpClient client = new OkHttpClient();
        OkHttpClient clientWith30sTimeOut = client.newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();


        restAdapter = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientWith30sTimeOut)
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
