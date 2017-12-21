package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 2/8/16.
 */
@Data
public class FarmerLandDetailsDto implements Serializable{

    Long id;
    DpcTalukDto dpcTalukDto;
    DpcVillageDto dpcVillageDto;
    DpcDistrictDto dpcDistrictDto;
    long createdBy;
    long createdDate;
    long modifiedBy;
    long modifiedDate;
    PaddyCategoryDto paddyCategoryDto;
    LandTypeDto landTypeDto;
    String mainLandLordName;
    String mainLandLordLname;
    String loanBookNumber;
    String surveyNumber;
    Double area;
    Double sowedAccumulated;
    Double sowedNonAccumulated;
    Double sowedTotal;
    Double expectedAccumulated;
    Double expectedNonAccumulated;
    Double expectedTotal;
    String expectedProcuringDate;
    String pattaNumber;

    FarmerRegistrationRequestDto farmerRegistrationRequestDto;
}
