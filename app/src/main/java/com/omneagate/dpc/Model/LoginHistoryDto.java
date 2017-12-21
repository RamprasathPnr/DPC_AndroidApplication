package com.omneagate.dpc.Model;

import android.database.Cursor;

import com.omneagate.dpc.Utility.DbConstants;
import com.omneagate.dpc.Utility.SessionId;

import lombok.Data;

/**
 * Created by user on 12/9/16.
 */
@Data
public class LoginHistoryDto {
    String loginTime;

    String loginType;

    long userId;

    String logoutTime;

    String logoutType;

    long dpcId;

    String transactionId;

    String deviceId;

    String sessionid;

    public LoginHistoryDto() {

    }

    public LoginHistoryDto(Cursor cur) {
        loginTime = cur.getString(cur.getColumnIndex(DbConstants.KEY_LOGIN_TIME));
        loginType = cur.getString(cur.getColumnIndex(DbConstants.KEY_LOGIN_TYPE));
        logoutTime = cur.getString(cur.getColumnIndex(DbConstants.KEY_LOGOUT_TIME));
        logoutType = cur.getString(cur.getColumnIndex(DbConstants.KEY_LOGOUT_TYPE));
        transactionId = cur.getString(cur.getColumnIndex(DbConstants.KEY_TRANSACTION_ID));
        dpcId = cur.getLong(cur.getColumnIndex(DbConstants.KEY_USER_DPC_ID));
        userId = cur.getLong(cur.getColumnIndex(DbConstants.KEY_USER_ID));
        try {
            sessionid = SessionId.getInstance().getSessionId();
        }
        catch(Exception e) {

        }
    }
}
