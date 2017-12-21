package com.omneagate.dpc.Model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

/**
 * Created by user on 21/7/16.
 */
@Data
public class FirstSyncTalukDto {

    /**Unique id*/
    long id;

    /**Name of the taluk*/
    String name;

    /**two digit taluk code*/
    String code;

    /**district id of taluk*/
    DistrictDto districtDto;

    /**the status of the state.*/
    boolean status ;

    /**The State CreatedBy.*/
    Long  createdById;

    /**The State Created on Date.*/
    long createdDate;

    /**The State CreatedBy.*/

    Long  modifiedById;

    /**The State Modified on Date.*/

    long modifiedDate;

    /**name of the taluk in Tamil(Local) font*/
    String ltalukName;

    /**collection of the village*/
    Set<VillageDto> villages=new HashSet<VillageDto>();

}
