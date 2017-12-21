package com.omneagate.dpc.Model;

import lombok.Data;

/**
 * Created DpcPOSHealthStatisticsDto.
 */
@Data
public class DpcPOSHealthStatisticsDto extends BaseResponseDto {
    long id;
    String deviceNum;
    int health;
    int scale;
    int level;
    int batteryLevel;
    int plugged;
    int status;
    String technology;
    int temperature;
    int voltage;
    boolean present;
    String latitude;
    String longtitude;
    int versionNum;
    long lastUpdatedTime;
    String versionName;
    long apkInstalledTime;
    String cpuUtilisation;
    String memoryUsed;
    String memoryRemaining;
    String totalMemory;
    String networkInfo;
    String hardDiskSizeFree;
    String userId;
    String simId;
    long createdTime;
    Integer noOfAssociatedFarmers;
    Integer noOfRequestedFarmers;
    Integer noOfUnSyncedProcurement;
    Integer noOfUnSyncedTruckMemo;
    Integer noOfUnSyncedVouchers;
}