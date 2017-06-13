package com.roka.geopathtrace.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;


/**
 * Created by Roka on 11/06/2017 
 */


public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public T data;
    String TAG = getClass().getSimpleName();

    BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(T data) {
        this.data = data;
        populateData();
    }

    abstract void populateData();
}
