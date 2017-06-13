package com.roka.geopathtrace.presenter;

import android.graphics.Color;
import android.os.Bundle;

import com.annimon.stream.Stream;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.roka.geopathtrace.model.common.LocationResponseModel;
import com.roka.geopathtrace.presenter.ipresenter.IHomeActivityPresenter;
import com.roka.geopathtrace.view.iview.IHomeActivityView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roka on 11/06/2017.
 */

public class HomeActivityPresenter extends BasePresenter implements IHomeActivityPresenter {

    private IHomeActivityView iHomeActivityView;
    private ArrayList<LatLng> points;
    private List<LocationResponseModel> locationList;

    public HomeActivityPresenter(IHomeActivityView iView) {
        super(iView);
        iHomeActivityView = iView;
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        locationList = new ArrayList<>();
    }

    @Override
    public void onSwipedToStart() {
        locationList.clear();
        iHomeActivityView.getDaoSession().getLocationResponseModelDao().deleteAll();

    }

    @Override
    public void onTapToStop() {
        if (iHomeActivityView.getDaoSession().getLocationResponseModelDao().loadAll().size() > 1) {
            //clears all Markers and Polylines
            points = new ArrayList<LatLng>();
            locationList.addAll(iHomeActivityView.getDaoSession().getLocationResponseModelDao().loadAll());
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            Stream.of(locationList).map(locationResponseModel -> new LatLng(locationResponseModel.getmLatitude(), locationResponseModel.getmLongitude()))
                    .forEach(latLng -> {
                        points.add(latLng);
                        builder.include(latLng);
                    });
            options.addAll(points);
            CircleOptions circleStartOptions = new CircleOptions();
            circleStartOptions.center(points.get(0)).strokeColor(Color.WHITE).fillColor(Color.GRAY).radius(1.5);
            CircleOptions circleStopOptions = new CircleOptions();
            circleStopOptions.center(points.get(points.size() - 1)).strokeColor(Color.WHITE).fillColor(Color.BLUE).radius(1.5);
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(), 100);
            iHomeActivityView.setMapObjects(circleStartOptions, circleStopOptions, iHomeActivityView.getCodeSnippet().getTimeDiff(locationList.get(0).getmDate(), locationList.get(locationList.size() - 1).getmDate()), options, cu);
        }
    }
}
