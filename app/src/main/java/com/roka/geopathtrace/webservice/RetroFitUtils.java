package com.roka.geopathtrace.webservice;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetroFitUtils {
    //Single ton object...
    private static RetroFitUtils RetroFitUtility = null;
    private final String TAG = "RetroFitUtils";

    //Single ton method...
    public static RetroFitUtils getInstance() {
        if (RetroFitUtility != null) {
            return RetroFitUtility;
        } else {
            RetroFitUtility = new RetroFitUtils();
            return RetroFitUtility;
        }
    }


    public void retrofitEnqueue(Call<ResponseBody> call, final ResponseListener resListener, final int flag) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e(TAG, "response.code(): " + response.code());
                Log.e(TAG, "response.code(): " + response.raw().code());

                Log.e(TAG, "=" + response.raw());

                String resultRes = null;
                if (response.body() != null) {
                    resultRes = getStringFromByte(response.body().byteStream());
                    Log.e(TAG, "=" + resultRes);
                }

                switch (response.code()) {
                    case 200:
                        resListener.showDialog(resultRes, flag);
                        break;
                    case 201:
                        resListener.onSuccess(resultRes, flag);
                        break;
                    case 400:
                        if (response.errorBody() != null) {
                            resListener.showErrorDialog(getStringFromByte(response.errorBody().byteStream()), flag);
                        }
                        break;
                    case 401:
                        resListener.logOut(flag);
                        break;
                    case 500:
                        resListener.showInternalServerErrorDialog(resultRes, flag);
                        break;
                    default:
                        resListener.logOut(flag);
                        break;
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                resListener.onFailure(t, flag);
            }
        });
    }



    public String getStringFromByte(InputStream inputStream) {

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String result = sb.toString();
        return result;
    }
}
