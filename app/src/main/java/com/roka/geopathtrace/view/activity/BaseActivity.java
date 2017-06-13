package com.roka.geopathtrace.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.roka.geopathtrace.common.GeoPathTraceApplication;
import com.roka.geopathtrace.model.common.DaoSession;
import com.roka.geopathtrace.presenter.ipresenter.IPresenter;
import com.roka.geopathtrace.util.CodeSnippet;
import com.roka.geopathtrace.view.iview.IView;
import com.roka.geopathtrace.view.widget.CustomProgressbar;
import com.roka.library.CustomException;
import com.roka.library.ExceptionTracker;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Roka on 11/06/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements IView {
    protected String TAG = getClass().getSimpleName();
    protected View mParentView;
    @Inject
    DaoSession daoSession;
    @Inject
    CodeSnippet mCodeSnippet;
    ProgressDialog pDialog;
    private IPresenter iPresenter;
    private CustomProgressbar mCustomProgressbar;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        injectViews();
        injectDagger();
        bindViews();
        initPresenter();
    }

    private void injectDagger() {
        ((GeoPathTraceApplication) getApplication()).getAppComponent().inject(this);
    }

    protected abstract void initPresenter();

    protected abstract void bindViews();

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        mParentView = getWindow().getDecorView().findViewById(android.R.id.content);
        return super.onCreateView(name, context, attrs);
    }

    private void injectViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (iPresenter != null) iPresenter.onStartPresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (iPresenter != null) iPresenter.onStopPresenter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (iPresenter != null) iPresenter.onPausePresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (iPresenter != null) iPresenter.onResumePresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPresenter != null) iPresenter.onDestroyPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (iPresenter != null) iPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }

    public void bindPresenter(IPresenter iPresenter) {
        this.iPresenter = iPresenter;
    }

    private CustomProgressbar getProgressBar() {
        if (mCustomProgressbar == null) {
            mCustomProgressbar = new CustomProgressbar(this);
        }
        return mCustomProgressbar;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(CustomException e) {
        Toast.makeText(this, e.getException(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressbar() {
        // TODO have to menu_view_question_preference the custom progressbar
        getProgressBar().show();
    }

    public void showLoadingDialog(Context context) {

        pDialog = new ProgressDialog(context);
        pDialog.setMessage(Html.escapeHtml("<b>Please wait auto read otp processing...</b>"));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void closeLoadingDialog() {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

    }

    @Override
    public void dismissProgressbar() {
        // TODO dismiss the progressbar
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getProgressBar().dismissProgress();
                } catch (Exception e) {
                    ExceptionTracker.track(e);
                }
            }
        });
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void showSnackBar(String message) {
        if (mParentView != null) {
            Snackbar snackbar = Snackbar.make(mParentView, message, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }

    @Override
    public void showSnackBar(@NonNull View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void showNetworkMessage() {
        if (mParentView != null) {
            Snackbar snackbar = Snackbar.make(mParentView, "No Network found!", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.setAction("Settings", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeSnippet.showNetworkSettings();
                }
            });
            snackbar.show();
        }
    }

    @Override
    public CodeSnippet getCodeSnippet() {
        return mCodeSnippet;
    }

    /**
     * Returns the layout id.
     *
     * @return
     */

    protected abstract int getLayoutId();
}