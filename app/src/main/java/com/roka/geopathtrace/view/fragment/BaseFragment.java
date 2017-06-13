package com.roka.geopathtrace.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roka.library.CustomException;
import com.roka.geopathtrace.presenter.ipresenter.IPresenter;
import com.roka.geopathtrace.util.CodeSnippet;
import com.roka.geopathtrace.view.iview.IView;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment implements IView {

    protected String TAG = getClass().getSimpleName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @Override
    public void bindPresenter(IPresenter iPresenter) {
        // nothing to implement here!
    }

    @Override
    public void showMessage(String message) {
        ((IView) getActivity()).showMessage(message);
    }

    @Override
    public void showMessage(int resId) {
        ((IView) getActivity()).showMessage(resId);
    }

    @Override
    public void showMessage(CustomException e) {
        ((IView) getActivity()).showMessage(e);
    }

    @Override
    public void showProgressbar() {
        ((IView) getActivity()).showProgressbar();
    }

    @Override
    public void dismissProgressbar() {
        try {
            ((IView) getActivity()).dismissProgressbar();
        } catch (Exception e) {

        }

    }

    @Override
    public void showSnackBar(String message) {
        ((IView) getActivity()).showSnackBar(message);
    }

    @Override
    public void showNetworkMessage() {
        ((IView) getActivity()).showNetworkMessage();
    }

    @Override
    public CodeSnippet getCodeSnippet() {
        return ((IView) getActivity()).getCodeSnippet();
    }

    @Override
    public void showSnackBar(@NonNull View view, String message) {
        ((IView) getActivity()).showSnackBar(view, message);
    }

    protected abstract int getLayoutId();

}
