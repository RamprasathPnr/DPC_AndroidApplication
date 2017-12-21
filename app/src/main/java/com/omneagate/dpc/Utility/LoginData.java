package com.omneagate.dpc.Utility;



import com.omneagate.dpc.Model.ResponseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * SingleTon class for maintain the sessionId
 */
public class LoginData {
    private static com.omneagate.dpc.Utility.LoginData mInstance = null;

    @Getter
    @Setter
    private ResponseDto responseData;


    @Getter
    @Setter
    private long fpsId;

    private LoginData() {
        responseData = new ResponseDto();
    }

    public static synchronized com.omneagate.dpc.Utility.LoginData getInstance() {
        if (mInstance == null) {
            mInstance = new com.omneagate.dpc.Utility.LoginData();
        }
        return mInstance;
    }

}
