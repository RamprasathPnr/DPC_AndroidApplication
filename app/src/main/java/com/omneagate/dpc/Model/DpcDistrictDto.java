package com.omneagate.dpc.Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

/**
 * Created by user on 7/9/16.
 */
@Data
public class DpcDistrictDto implements Serializable{


    public Long id;

    /**
     * district name
     */
    public String name;

    /**
     * district code
     */

    public String code;

    /**
     * Reference to State table
     */

    public DpcStateDto dpcStateDto;

    /**
     * Status of the District.
     */

    public boolean status;

    /**
     * The District CreatedBy.
     */

    public Long createdById;

    /**
     * The District Created on Date.
     */

    public Long createdDate;

    /**
     * The District ModifiedBy.
     */

    public Long modifiedById;

    /**
     * The District Modified date.
     */

    public Long modifiedDate;

    /**
     * name of the district in Tamil(Local) font
     */

    public String ldistrictName;


    public String districtSName;


    Set<DpcTalukDto> taluks = new HashSet<DpcTalukDto>();

}
