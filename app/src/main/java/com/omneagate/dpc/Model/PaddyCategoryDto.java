package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 27/7/16.
 */
@Data
public class PaddyCategoryDto implements Serializable {
    long id;
    String name;
    String lname;
    String code;
    boolean status;
    long createdBy;
    long modifiedBy;
    long createdDate;
    long modifiedDate;
    double purchaseRatePerQunital;
    double bonusRatePerQuintal;
    double totalRatePerQunital;

    PaddyGradeDto paddyGradeDto;
}
