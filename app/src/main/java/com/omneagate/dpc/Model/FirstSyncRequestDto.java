package com.omneagate.dpc.Model;

import lombok.Data;

/**
 * Created by user on 19/7/16.
 */
@Data
public class FirstSyncRequestDto  {

    String deviceNumber;
    String tableName;
    int totalCount;
    int currentCount;
    int totalSentCount;
    String refNum;
    String lastSyncTime;
    boolean isEndOfSynch;
}
