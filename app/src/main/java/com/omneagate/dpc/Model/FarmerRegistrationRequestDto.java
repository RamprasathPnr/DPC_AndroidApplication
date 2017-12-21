package com.omneagate.dpc.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by user on 2/8/16.
 */
@Data
public class FarmerRegistrationRequestDto implements Serializable {

    long id;
    DPCProfileDto dpcProfileDto;
    String farmerName;
    String farmerLname;
    long dateOfBirth;
    String farmerCode;
    String channel; //G2G_Client, DPC_POS_APP//
    String guardian;  // Father or Husband
    String guardianName;
    String guardianLname;
    String address1;
    String address2;

    DpcDistrictDto dpcDistrictDto;
    DpcTalukDto dpcTalukDto;
    DpcVillageDto dpcVillageDto;

    String villagePanchayat;
    long createdBy;
    long createdDate;
    long modifiedBy;
    long modifiedDate;
    String mobileNumber;
    SocietyDto societyDto;
    FarmerClassDto farmerClassDto;
    String loanAccountNumber;
    String aadhaarNumber;
    BankDto bankDto;
    String accountNumber;
    String branchName;
    String ifscCode;
    Boolean status;
    String requestedReferenceNumber;
    String pincode;
    String deviceNumber;
    String rationCardNumber;
    List<FarmerLandDetailsDto> farmerLandDetailsDtoList;
}
