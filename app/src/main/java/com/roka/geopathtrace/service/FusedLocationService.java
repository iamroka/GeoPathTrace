package com.roka.geopathtrace.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.roka.geopathtrace.common.Constants;
import com.roka.geopathtrace.common.GeoPathTraceApplication;
import com.roka.geopathtrace.model.common.DaoSession;
import com.roka.geopathtrace.model.common.LocationResponseModel;
import com.roka.geopathtrace.util.CodeSnippet;

import java.util.Date;

import javax.inject.Inject;


/**
 * Created by Roka on 11/06/2017.
 */
public class FusedLocationService extends Service implements
        LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Inject
    DaoSession daoSession;
    @Inject
    CodeSnippet codeSnippet;
    GoogleApiClient mGoogleApiClient;
    double mLatitude, mLongitude, mCurrentLat, mCurrentLang;
    private String TAG = getClass().getCanonicalName();
    private LocationRequest mLocationRequest;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setMaxWaitTime(Constants.Values.INTERVAL);
        mLocationRequest.setFastestInterval(Constants.Values.FASTEST_INTERVAL);
        mLocationRequest.setSmallestDisplacement(Constants.Values.SMALLEST_DISPLACEMENT);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressWarnings("MissingPermission")
    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((GeoPathTraceApplication) getApplication()).getAppComponent().inject(this);
        if (codeSnippet.checkPlayServices(getApplicationContext(), new CodeSnippet.OnGooglePlayServiceListener() {
            @Override
            public void onInstallingService() {

            }

            @Override
            public void onCancelServiceInstallation() {

            }
        })) {
            createLocationRequest();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            mGoogleApiClient.connect();
        } else {
            stopSelf();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected->" + mGoogleApiClient.isConnected());
        //get current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged");
        if (location != null) {
            mCurrentLat = location.getLatitude();
            mCurrentLang = location.getLongitude();
            LocationResponseModel locationResponseModel = new LocationResponseModel(mCurrentLat, mCurrentLang, new Date());
            daoSession.getLocationResponseModelDao().insert(locationResponseModel);
        }
    }

}
