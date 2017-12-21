package com.omneagate.dpc.Model;

import android.database.Cursor;


import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 17/8/16.
 */
@Data
public class DPCSyncExceptionDto extends  BaseResponseDto implements Serializable {

    long dpcProfileId;

    int localId;

    String syncMode;

    String tableName;

    String action;

    long recordId;

    long lastSyncTime;

    String rawData;

    String syncErrorDescription;

    String deviceNumber ;

    public DPCSyncExceptionDto() {

    }


    public DPCSyncExceptionDto(Cursor cur) {
        localId = cur.getInt(cur.getColumnIndex("_id"));
        syncMode = cur.getString(cur.getColumnIndex("syncMode"));
        tableName = cur.getString(cur.getColumnIndex("tableName"));
        action = cur.getString(cur.getColumnIndex("action"));
        recordId = Long.valueOf(cur.getString(cur.getColumnIndex("recordId")));
        lastSyncTime = Long.valueOf(cur.getString(cur.getColumnIndex("lastSyncTime")));
        rawData = cur.getString(cur.getColumnIndex("rawData"));
        syncErrorDescription = cur.getString(cur.getColumnIndex("errorDescription"));
        dpcProfileId = cur.getLong(cur.getColumnIndex("dpcId"));
        deviceNumber = cur.getString(cur.getColumnIndex("device_id"));
    }
}
