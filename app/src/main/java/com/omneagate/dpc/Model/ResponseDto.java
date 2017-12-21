package com.omneagate.dpc.Model;

import com.omneagate.dpc.Model.EnumModel.DeviceStatus;

import java.util.Set;

import lombok.Data;

/**
 * Created by user1 on 25/5/16.
 */
@Data
public class ResponseDto extends BaseResponseDto {
    private static ResponseDto mInstance = null;

    UserDetailDto userDetailDto;
    DPCProfileDto dpcProfileDto;
    DeviceStatus deviceStatus;
    long serverCurrentDateTime;


   /* FirstSyncResponseDto firstSyncResponseDto;
    VillageDto villageDto;
    BankDto bankDetailsDto;
    FirstSyncTalukDto talukDto;
    CropNameDto cropNameDto;
    LandTypeDto landTypeDto;*/

    //List<MenuDataDto> menudto;
    public static synchronized ResponseDto getInstance() {
        if (mInstance == null) {
            mInstance = new ResponseDto();
        }
        return mInstance;
    }

}
