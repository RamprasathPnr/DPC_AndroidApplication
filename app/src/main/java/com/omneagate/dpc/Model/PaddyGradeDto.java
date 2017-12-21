package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 3/8/16.
 */
@Data
public class PaddyGradeDto implements Serializable{

    Long id;


    String name;

    String lname;


    String code;


    long createdBy;


    long createdDate;


    long modifiedBy;


    long modifiedDate;


    boolean status;
}
