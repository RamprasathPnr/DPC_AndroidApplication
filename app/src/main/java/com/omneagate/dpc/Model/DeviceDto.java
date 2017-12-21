package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 12/9/16.
 */
@Data
public class DeviceDto implements Serializable {
    Long id;
    /** SIM Card number associated with the device*/
//    SimDto simDto;
    /**
     * deviceNumber of the pos
     */
    String deviceNumber;
    /**
     * Status check -
     */
    Boolean active;
    /**
     * created By
     */
    Long createdBy;
    /**
     * created on date
     */
    Long createdDate;
    /**
     * modified by
     */
    Long modifiedBy;
    /**
     * modified on date
     */
    Long modifiedDate;
    /**
     * Make
     */
    String make;
    /***f
     * serial number
     */
    String serialNumber;
    /**
     * model number
     */
    String model;

    Boolean associated = false;
    //    HeartBeatSyncStatus deviceWorkingStatus;
    DpcDeviceDetailsDto deviceDetailsDto;
    String code;
    String lastSyncdOn;
    String searchCode;
    String searchAppType;
    DPCProfileDto dpcProfileDto;
}
