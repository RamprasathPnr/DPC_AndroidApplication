package com.omneagate.dpc.Model;

import com.omneagate.dpc.Model.EnumModel.CommonStatuses;

import lombok.Data;

/**
 * Created by root on 4/8/16.
 */
@Data
public class UpgradeDto {

    private static final long serialVersionUID = 1L;


    long id;


    long createdTime;


    long updatedTime;


    String deviceNum;


    int previousVersion;


    int currentVersion;


    int stateCount;


    int districtCount;


    int talukCount;


    int villageCount;


    int dpcSocietyCount;


    int farmerClassCount;


    int bankCount;


    int paddyCategoryCount;


    int landTypeCount;


    String referenceNumber;


    CommonStatuses status;


  // ApplicationType applicationType;

}
