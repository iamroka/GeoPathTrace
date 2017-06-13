package com.roka.geopathtrace.di;

import android.content.Context;

import com.roka.geopathtrace.common.GeoPathTraceApplication;
import com.roka.geopathtrace.model.common.DaoMaster;
import com.roka.geopathtrace.model.common.DaoSession;
import com.roka.geopathtrace.util.CodeSnippet;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roka on 6/12/2017.
 */
@Module
public class AppModule {
    GeoPathTraceApplication mApplication;

    public AppModule(GeoPathTraceApplication mApplication) {
        this.mApplication = mApplication;
    }

    @Singleton
    @Provides
    public Context providesContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    public DaoSession providesDaoSession(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "geo-trace-db");
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    @Singleton
    @Provides
    public CodeSnippet provideCodeSnippet(Context context) {
        return new CodeSnippet(context);
    }
}
