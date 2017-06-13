package com.roka.geopathtrace.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Roka on 11/06/2017 
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class BaseResponse {

    private int status;
    private String message;

    public BaseResponse() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
