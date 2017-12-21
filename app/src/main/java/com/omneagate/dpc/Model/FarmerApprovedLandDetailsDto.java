package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 9/9/16.
 */
@Data
public class FarmerApprovedLandDetailsDto  implements Serializable {
    Long id;
    DPCProfileDto dpcProfileDto;
    DpcDistrictDto dpcDistrictDto;
    DpcTalukDto dpcTalukDto;
    DpcVillageDto dpcVillageDto;
    Long createdBy;
    Long createdDate;
    Long modifiedBy;
    Long modifiedDate;
    PaddyCategoryDto paddyCategoryDto;
    LandTypeDto landTypeDto;
    String mainLandLordName;
    String mainLandLordLname;
    String surveyNumber;
    Double area;
    Double sowedAccumulated;
    Double sowedNonAccumulated;
    Double sowedTotal;
    Double expectedAccumulated;
    Double expectedNonAccumulated;
    Double expectedTotal;
    String pattaNumber;
    Long expectedProcuringDate;
    FarmerRegistrationDto farmerRegistrationDto;

}
