package com.roka.geopathtrace.common;

/**
 * Created by Roka on 11/06/2017.
 */

public interface Constants {


    interface BundleKey {
        String BROADCAST_MESSAGE = "broadcastAction";
        String PHONE_NUMBER = "phoneNumber";
        String COUNTRY_CODE = "countryCode";
        String CHAT_USER_DETAILS = "chatUserDetails";
        String LOCATION_BUNDLE = "locationBundle";
    }

    interface RequestCodes {
        int PERMISSION_GPS = 100;
    }

    interface ApiRequestKey {
        int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1001;
    }

    interface Permission {
        int SMS_READ_REQUEST_CODE = 10001;
    }
    interface Values{
        long INTERVAL = 1000 * 30;
        long FASTEST_INTERVAL = 1000 * 5; // 15 sec
        float SMALLEST_DISPLACEMENT = 0.25F;
    }


}
