package com.roka.geopathtrace.di;

import com.roka.geopathtrace.common.GeoPathTraceApplication;
import com.roka.geopathtrace.service.FusedLocationService;
import com.roka.geopathtrace.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Roka on 6/12/2017.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(GeoPathTraceApplication application);

    void inject(BaseActivity baseActivity);

    void inject(FusedLocationService locationService);
}
