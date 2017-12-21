package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 25/10/16.
 */
@Data
public class DpcSpecificationDto implements Serializable {

    Long id;
    String specificationType;
    Double moisturePercentageFrom;
    Double moisturePercentageTo;
    long startDate;
    long endDate;
    boolean status;
    Long createdBy;
    long createdDate;
    Long modifiedBy;
    long modifiedDate;
    Long specificationCode;
}
