package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 22/7/16.
 */
@Data
public class FirstSyncDistrictDto implements Serializable {

    /** auto generated id */

    long id;

    /** district code */

    String code;

    /** district name */

    String name;

    /** state id */

    StateDto stateDto;

    /**The Status of the District.*/

    boolean status ;

    /**The District CreatedBy.*/

    long createdById;

    /**The District Created on Date.*/

    long createdDate;

    /**The District ModifiedBy.*/

    long modifiedById;


    long modifiedDate;

    /**name of the District in Tamil(Local) font*/

    String ldistrictName;

    /** collection of taluks */

//    Set<TalukDto> taluks=new HashSet<TalukDto>();
}
