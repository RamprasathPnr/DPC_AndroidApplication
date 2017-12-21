package com.omneagate.dpc.Model;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

/**
 * Created by user on 20/7/16.
 */
@Data
public class TalukDto implements Serializable {

    long id;
    String trackId;
    String createdById;
    String status;
    String statusCode;
    String modifiedById;
    String ltalukName;
    String name;
    String createdDate;
    String errorDescription;
    DistrictDto districtDto;



}
