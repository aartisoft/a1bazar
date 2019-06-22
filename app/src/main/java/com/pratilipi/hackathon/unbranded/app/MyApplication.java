package com.pratilipi.hackathon.unbranded.app;

import android.support.multidex.MultiDexApplication;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor.Level;
import com.pratilipi.hackathon.unbranded.BuildConfig;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.utils.AppLogger;
import com.pratilipi.hackathon.unbranded.utils.NotificationChannelHelper;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MyApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();


        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());

        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(Level.BODY);
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

    }


}
