package com.roka.geopathtrace.presenter.ipresenter;

import com.roka.geopathtrace.presenter.ipresenter.IPresenter;

/**
 * Created by Roka on 11/06/2017. 
 */

public interface IHomeActivityPresenter extends IPresenter {
    void onSwipedToStart();

    void onTapToStop();
}