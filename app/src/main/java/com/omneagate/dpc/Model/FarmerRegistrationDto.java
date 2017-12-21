package com.omneagate.dpc.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by user on 2/9/16.
 */
@Data
    public class FarmerRegistrationDto extends BaseResponseDto implements Serializable{
    Long id;
    String farmerName;
    String farmerLname;
    Long dateOfBirth;
    String farmerCode;
    String guardian; // Father or Husband
    String guardianName;
    String guardianLname;
    String address1;
    String address2;
    String villagePanchayat;
    String mobileNumber;
    String loanAccountNumber;
    String aadhaarNumber;
    String accountNumber;
    String branchName;
    String ifscCode;
    String pinCode;
    Boolean status;
    Long createdBy;
    Long createdDate;
    Long modifiedBy;
    Long modifiedDate;
    DPCProfileDto dpcProfileDto;
    DpcDistrictDto dpcDistrictDto;
    DpcTalukDto dpcTalukDto;
    DpcVillageDto dpcVillageDto;
    BankDto bankDto;
    String rationCardNumber;
    List<FarmerLandDetailsDto> farmerLandDetailsDtos;
    List<FarmerApprovedLandDetailsDto> farmerApprovedLandDetailsDtos;
}
