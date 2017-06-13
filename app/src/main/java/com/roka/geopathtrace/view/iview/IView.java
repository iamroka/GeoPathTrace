package com.roka.geopathtrace.view.iview;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.roka.geopathtrace.model.common.DaoSession;
import com.roka.geopathtrace.util.CodeSnippet;
import com.roka.geopathtrace.presenter.ipresenter.IPresenter;
import com.roka.library.CustomException;

/**
 * Created by Roka on 11/06/2017 
 */

public  interface IView {

    void showMessage(String message);

    void showMessage(int resId);

    void showMessage(CustomException e);

    void showProgressbar();

    void dismissProgressbar();

    FragmentActivity getActivity();

    void showSnackBar(String message);

    void showSnackBar(@NonNull View view, String message);

    void showNetworkMessage();

//    CodeSnippet getCodeSnippet();

    void bindPresenter(IPresenter iPresenter);


    CodeSnippet getCodeSnippet();
    DaoSession getDaoSession();
}
