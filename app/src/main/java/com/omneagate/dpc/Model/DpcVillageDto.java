package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 7/9/16.
 */
@Data
public class DpcVillageDto implements Serializable {

    Long id;
    String name;
    String code;
    boolean status;
    Long createdById;
    Long createdDate;
    Long modifiedById;
    Long modifiedDate;
    String lvillageName;
    String newVillageCode;
    DpcTalukDto dpcTalukDto;
}
