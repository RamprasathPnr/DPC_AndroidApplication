package com.omneagate.dpc.Model;


import java.io.Serializable;

import lombok.Data;

/**
 * Created by root on 23/9/16.
 */
@Data
public class DpcCapDto extends BaseResponseDto implements Serializable {

    Long id;
    String contactNum;
    String contactPerson;
    Long modifiedDate;
    Long modifiedby;
    Long createdDate;
    Long createdby;
    boolean geofencing;
    String latitude;
    String longitude;
    String name;
    String pinCode;
    Long version;
    String	capType;
    String capCategory;
    String storageCapacity;
    String weighingCapacity;
    Long noofPeople;
    String address;
    String emailAddress;
    String generatedCode;
    boolean remoteLogEnabled;
    boolean status;
    String lname;
    Long platformScales;
    Long moistureMeter;
    Long winnowingMachine;
    DpcDistrictDto dpcDistrictDto;
    DpcTalukDto dpcTalukDto;
    DpcVillageDto dpcVillageDto;
    DeviceDto deviceDto;
}
