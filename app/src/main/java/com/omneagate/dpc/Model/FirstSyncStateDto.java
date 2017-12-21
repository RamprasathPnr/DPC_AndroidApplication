package com.omneagate.dpc.Model;

import lombok.Data;

/**
 * Created by user on 28/7/16.
 */
@Data
public class FirstSyncStateDto  {

    long id;
    String name;
    String code;
    boolean status;
    long  createdById;
    long createdDate;
    public long modifiedById;
    long modifiedDate;
    String lstateName;
//    Set<DistrictDto> districts;
}
