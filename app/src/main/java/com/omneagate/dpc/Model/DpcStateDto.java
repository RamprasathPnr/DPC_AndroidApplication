package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 7/9/16.
 */
@Data
public class DpcStateDto implements Serializable {


    public Long id;

    public String name;

    public String code;

    public boolean status;

    /**
     * The State CreatedBy.
     */

    public Long createdById;

    /**
     * The State Created on Date.
     */

    public Long createdDate;

    /**
     * The State CreatedBy.
     */

    public Long modifiedById;

    /**
     * The State Modified date.
     */

    public Long modifiedDate;

    /**
     * name of the state in Tamil(Local) font
     */

    String lstateName;
}
