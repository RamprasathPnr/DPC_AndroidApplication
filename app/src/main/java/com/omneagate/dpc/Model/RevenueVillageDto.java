package com.omneagate.dpc.Model;

import lombok.Data;

/**
 * Created by user on 2/9/16.
 */
@Data
public class RevenueVillageDto {
    Long id;

    String revVillageName;

    String villageCode;

    boolean status = true;

    Long createdById;

    Long createdDate;

    Long modifiedById;

    Long modifiedDate;

    String revVillageLname;

    FirstSyncTalukDto talukDto;
}
