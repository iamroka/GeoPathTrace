package com.roka.geopathtrace.presenter.ipresenter;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Roka on 11/06/2017 
 */

public interface IPresenter {

    void onCreatePresenter(Bundle bundle);

    void onStartPresenter();

    void onStopPresenter();

    void onPausePresenter();

    void onResumePresenter();

    void onDestroyPresenter();

    void onActivityResultPresenter(int requestCode, int resultCode, Intent data);

}
