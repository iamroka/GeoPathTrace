package com.roka.geopathtrace.view.iview;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.roka.geopathtrace.view.iview.IView;

/**
 * Created by Roka on 11/06/2017. 
 */

public interface IHomeActivityView extends IView {

    void setMapObjects(CircleOptions circleStartOptions, CircleOptions circleStopOptions, String timeDiff, PolylineOptions options, CameraUpdate cu);
}
