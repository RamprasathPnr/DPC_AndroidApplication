package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by root on 23/9/16.
 */
@Data
public class DpcTruckMemoDto extends BaseResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    Long id;

    PaddyCategoryDto paddyCategoryDto;

    PaddyGradeDto paddyGradeDto;

    Double moistureContent;

    int numberOfBags;

    double netQuantity;

    String transportType;

    String lorryNumber;

    String transporterName;

    String conditionOfGunny;

    double gunnyCapacity;

    DpcCapDto dpcCapDto;

    String truckMemoNumber;

    Long createdBy;

    Long createdDate;

    Long modifiedBy;

    Long modifiedDate;

    DeviceDto deviceDto;

    String serverTruckMemoNo;

    String txnDateTime;

    DPCProfileDto dpcProfileDto;

    String syncStatus; //Channel of Procurement online or offline
    DpcSpecificationDto dpcSpecificationDto;

}
