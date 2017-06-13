package com.roka.geopathtrace.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.greendao.annotation.Entity;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Roka on 6/12/2017.
 */
@Entity
public class LocationResponseModel{

    private double mLatitude;
    private double mLongitude;
    private Date mDate;

    public LocationResponseModel() {
    }

    @Generated(hash = 325698771)
    public LocationResponseModel(double mLatitude, double mLongitude, Date mDate) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mDate = mDate;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public double getMLatitude() {
        return this.mLatitude;
    }

    public void setMLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getMLongitude() {
        return this.mLongitude;
    }

    public void setMLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public Date getMDate() {
        return this.mDate;
    }

    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }


}