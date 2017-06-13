package com.roka.geopathtrace.webservice;
/**
 * Created by Roka on 11/06/2017 
 */
public interface ResponseListener {

    void onSuccess(String response, int flag);

    void onFailure(Throwable throwable, int flag);

    void showDialog(String response, int flag);

    void showErrorDialog(String errorResponse, int flag);

    void showInternalServerErrorDialog(String errorResponse, int flag);

    void logOut(int flag);

}
