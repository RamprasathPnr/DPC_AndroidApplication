package com.omneagate.dpc.Utility;

import java.util.Date;

import lombok.Data;

/**
 * SingleTon class for maintain the sessionId
 */
@Data
public class SessionId {

    private static SessionId mInstance = null;


    private String sessionId;


    private long userId;


    private String transactionId;


    private String userName;


    private boolean qrOTPEnabled = false;

     private String fpsCode;

    private long dpcId;

    private Date loginTime;

    private Date lastLoginTime;

    private SessionId() {
        sessionId = "";
        userName = "";
        fpsCode = "";
        userId = 0l;
        dpcId = 0l;
        transactionId = "";
        loginTime = new Date();
        lastLoginTime = new Date();
    }

    public static synchronized SessionId getInstance() {
        if (mInstance == null) {
            mInstance = new SessionId();
        }
        return mInstance;
    }

}
