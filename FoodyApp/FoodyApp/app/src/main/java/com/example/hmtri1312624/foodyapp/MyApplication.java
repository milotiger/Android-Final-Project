package com.example.hmtri1312624.foodyapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by M-Tae on 5/21/2016.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
