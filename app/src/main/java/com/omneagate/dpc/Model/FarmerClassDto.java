package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 27/7/16.
 */
@Data
public class FarmerClassDto implements Serializable {


    long id;
    String name;
    String lname;
    long code;
    boolean status;
    long createdBy;
    long modifiedBy;
    long createdDate;
    long modifiedDate;

    /*long id;
    String name;
    String lname;
    long code;
    boolean status;
    long createdBy;
    long modifiedBy;
    long createdDate;
    long modifiedDate;*/
}
