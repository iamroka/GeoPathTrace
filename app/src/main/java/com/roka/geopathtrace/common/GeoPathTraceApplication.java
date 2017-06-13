package com.roka.geopathtrace.common;

import android.content.Context;
import android.net.ConnectivityManager;

import android.app.Application;

import com.roka.geopathtrace.di.AppComponent;
import com.roka.geopathtrace.di.AppModule;
import com.roka.geopathtrace.di.DaggerAppComponent;


/**
 * Created by Roka on 11/06/2017 
 */
public class GeoPathTraceApplication extends Application {
    private static GeoPathTraceApplication mAppController;
    public static GeoPathTraceApplication getInstance() {
        return mAppController;
    }
    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppController = this;
        initDagger();

    }

    private void initDagger() {
        appComponent= DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .build();
        appComponent.inject(this);
    }

    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

}
