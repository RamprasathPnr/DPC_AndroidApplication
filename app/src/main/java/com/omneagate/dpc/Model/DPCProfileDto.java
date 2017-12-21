package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 28/7/16.
 */
@Data
public class DPCProfileDto implements Serializable {
    long id;
    String code;
    String contactNum;
    String contactPerson;
    Long modifiedDate;
    Long modifiedby;
    Long createdDate;
    Long createdby;
    Boolean geofencing;
    String latitude;
    String longitude;
    String name;
    Long pinCode;
    Long version;
    DeviceDto deviceDto;
    DistrictDto districtDto;
    TalukDto talukDto;
    String	dpcType;
//    PaginationDto paginationDto;
    String dpcCategory;
    Long storageCapacity;
    Long weighingCapacity;
    Long noofPeople;
    String address;
    String emailAddress;
    /** To show status(Open, Closed) in UI*/
    boolean status;
    UserDetailDto userDetailDto;
    String generatedCode;
    Boolean remoteLogEnabled;
    Boolean active;
    String lname;
    VillageDto villageDto;
    Long platformScales;
    Long moistureMeter;
    Long winnowingMachine;
    DpcDistrictDto dpcDistrictDto;
    DpcTalukDto dpcTalukDto;
    DpcVillageDto dpcVillageDto;
}


