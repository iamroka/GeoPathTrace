package com.roka.geopathtrace.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.roka.geopathtrace.R;
import com.roka.geopathtrace.common.Constants;
import com.roka.geopathtrace.presenter.HomeActivityPresenter;
import com.roka.geopathtrace.presenter.ipresenter.IHomeActivityPresenter;
import com.roka.geopathtrace.service.FusedLocationService;
import com.roka.geopathtrace.view.iview.IHomeActivityView;
import com.roka.geopathtrace.view.widget.SwipeButton;
import com.roka.geopathtrace.view.widget.SwipeOnStateChangeListener;

import butterknife.BindView;

/**
 * Created by Roka on 11/06/2017.
 */

public class HomeActivity extends BaseActivity implements IHomeActivityView, OnMapReadyCallback {
    @BindView(R.id.sb_start_geo_trace_button_home)
    SwipeButton vSbStartTrace;
    @BindView(R.id.tv_travel_duration_home)
    TextView vTvTravelTime;

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private IHomeActivityPresenter iHomeActivityPresenter;


    @Override
    protected void initPresenter() {
        iHomeActivityPresenter = new HomeActivityPresenter(this);
        iHomeActivityPresenter.onCreatePresenter(null);
    }

    @Override
    protected void bindViews() {

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        vSbStartTrace.setStateListener(new SwipeOnStateChangeListener() {
            @Override
            public void onStartClicked() {
                startService(new Intent(getActivity(), FusedLocationService.class));
                iHomeActivityPresenter.onSwipedToStart();
                if (googleMap != null)
                    googleMap.clear();
            }

            @Override
            public void onStopClicked() {
                stopService(new Intent(getActivity(), FusedLocationService.class));
                if (googleMap != null)
                    googleMap.clear();
                iHomeActivityPresenter.onTapToStop();
            }
        });
        if (getCodeSnippet().isAboveMarshmallow())
            requestLocationPermission();
        else
            getMyFusedLocation();


    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.RequestCodes.PERMISSION_GPS);
        } else {
            getMyFusedLocation();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


    @SuppressWarnings("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.RequestCodes.PERMISSION_GPS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMyFusedLocation();
                } else
                    requestLocationPermission();
                break;

        }
    }

    private void getMyFusedLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled && isNetworkEnabled) {
            //locationUpdateService();
            mapFragment.getMapAsync(this);
            //iHomeActivityPresenter.setupGoogleClient();

        } else {
            Toast.makeText(this, "Please enable GPS to proceed further", Toast.LENGTH_SHORT).show();
            getCodeSnippet().showGpsSettings(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestLocationPermission();
    }

    @Override
    public void setMapObjects(CircleOptions circleStartOptions, CircleOptions circleStopOptions, String timeDiff, PolylineOptions options, CameraUpdate cu) {
        if (googleMap!=null){
            vTvTravelTime.setText(timeDiff);
            googleMap.addCircle(circleStartOptions);
            googleMap.addCircle(circleStopOptions);
            googleMap.addPolyline(options);
            googleMap.animateCamera(cu);

        }
    }
}