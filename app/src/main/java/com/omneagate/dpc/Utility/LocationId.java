package com.omneagate.dpc.Utility;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * SingleTon class for maintain the sessionId
 */
@Data
public class LocationId {
    private static com.omneagate.dpc.Utility.LocationId mInstance = null;


    private String longitude;

    private String latitude;

    private LocationId() {
        longitude = null;
        latitude = null;
    }

    public static synchronized com.omneagate.dpc.Utility.LocationId getInstance() {
        if (mInstance == null) {
            mInstance = new com.omneagate.dpc.Utility.LocationId();
        }
        return mInstance;
    }

}
