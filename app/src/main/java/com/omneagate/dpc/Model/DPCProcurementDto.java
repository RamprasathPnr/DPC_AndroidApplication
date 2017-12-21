package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 19/9/16.
 */
@Data
public class DPCProcurementDto extends  BaseResponseDto implements Serializable {
    Long id;
    PaddyCategoryDto paddyCategoryDto;
    Double moistureContent;
    PaddyGradeDto paddyGradeDto;
    int lotNumber;
    double spillageQuantity;
    int numberOfBags;
    double netWeight;
    DpcPaddyRateDto dpcPaddyRateDto;
    double totalAmount;
    double gradeCut;
    double moistureCut;
    double additionalCut;
    double totalCut;
    double totalCutAmount;
    double netAmount;
    DPCProfileDto dpcProfileDto;
    FarmerRegistrationDto farmerRegistrationDto;
    Long createdBy;
    Long createdDate;
    Long modifiedBy;
    Long modifiedDate;
    String procurementReceiptNo;
    DeviceDto deviceDto;
    String serverProcurementBillNo;
    String txnDateTime;
    String lastSyncTime;
    String syncStatus; //Channel of Procurement online or offline
    String mode; //Procurement mode based on farmer code or Scanned Aadhaar Number
    long smsRefNumber; //Reference number from SMS provider
    String paddyCategoryName;
    String gradeName;
    DpcSpecificationDto dpcSpecificationDto;

}
