package com.omneagate.dpc.Model;

import java.util.Map;
import java.util.Set;

import lombok.Data;

/**
 * Created by user on 20/7/16.
 */
@Data
public class FirstSyncResponseDto {

    int totalCount;
    boolean hasMore;
    int currentCount;
    int totalSentCount;
    String tableName;
    String refNum;
    boolean firstFetch;
    String lastSyncTime;

    Map<String, Integer> tableDetails;

    String statusCode;

//    Set<VillageDto> villageDto;

    Set<BankDto> bankDto;

//    Set<FirstSyncTalukDto> talukDto;

    Set<CropNameDto> cropNameDto;

    Set<LandTypeDto> landTypeDto;

//    Set<FirstSyncDistrictDto> districtDto;

//    Set<DpcDistrictDto> districtDto;

    Set<SocietyDto> societyDto;

    Set<FarmerClassDto> farmerClassDto;

    Set<PaddyCategoryDto> paddyCategoryDto;

//    Set<FirstSyncStateDto> stateDto;

    Set<PaddyGradeDto> paddyGradeDto;

    Set<FarmerRegistrationRequestDto> farmerRegistrationRequestDto;

    Set<FarmerLandDetailsDto>  farmerLandDetailsDto;

    Set<RevenueVillageDto> revenueVillageDto;


    Set<DpcStateDto> dpcStateDtos;

    Set<DpcDistrictDto> dpcDistrictDtos;

    Set<DpcTalukDto> dpcTalukDtos;

    Set<DpcVillageDto> dpcVillageDtos;

    Set<FarmerRegistrationDto> farmerRegistrationDto;

    Set<FarmerApprovedLandDetailsDto> farmerApprovedLandDetailsDto;


    Set<DpcPaddyRateDto> dpcPaddyRateDtos;

    Set<DPCProcurementDto> dpcProcurementDtos;

    Set<DpcCapDto> dpcCapDtos;

    Set<DpcTruckMemoDto> dpcTruckMemoDtos;

    Set<DpcSpecificationDto> dpcSpecificationDto;
}
