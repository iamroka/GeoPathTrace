package com.roka.library;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.roka.geopathtrace.model.dto.response.BaseResponse;

/**
 * Created by Roka on 11/06/2017 
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class CustomException extends Exception {

    private boolean code;
    private String exception;

    public CustomException(boolean code, String exception) {
        this.code = code;
        this.exception = exception;
    }

    public CustomException(boolean code, Throwable throwable) {
        this.code = code;
        this.exception = throwable.getMessage();
    }

    public CustomException(BaseResponse response){
        Log.d("CustomException","\ncode: "+response.getStatus()+"\n"+response.getMessage());
        this.exception = response.getMessage();
    }

    public CustomException(boolean code, BaseResponse response){
        this.code = code;
        this.exception = response.getMessage();
    }

    public CustomException(){}

    public boolean getCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
