package com.omneagate.dpc.Model;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

/**
 * Created by user on 20/7/16.
 */
@Data
public class VillageDto implements Serializable {


    long id;

    /** Name of the village */

    String name;

    /** two digit village code */

    String code;

    /**the status of the state.*/


    boolean status;


    long  createdById;

    /**The State Created on Date.*/

    long createdDate;

    /**The Village ModifiedBy.*/

    long modifiedById;

    /**The State Modified on Date.*/

    long modifiedDate;

    /**name of the village in Tamil(Local) font*/

    String lvillageName;

    /** taluk id of the village */

    TalukDto talukDto;

    /**for pagination purpose*/

    //PaginationDto PaginationDto;

}
