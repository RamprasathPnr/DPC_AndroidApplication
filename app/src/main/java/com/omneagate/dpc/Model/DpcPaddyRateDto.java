package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 19/9/16.
 */
@Data
public class DpcPaddyRateDto implements Serializable{
    long id;
//    PaddyCategoryDto paddyCategoryDto;
    double purchaseRate;
    double bonusRate;
    double totalRate;
    Long validityDateFrom;
    Long validityDateTo;
    Long createdById;
    Long createdDate;
    Long modifiedById;
    Long modifiedDate;
    boolean status;
    PaddyGradeDto paddyGradeDto;


    /*@Getter @Setter
	Long id;

    @Getter
    @Setter
    double purchaseRate;

    @Getter
    @Setter
    double bonusRate;

    @Getter
    @Setter
    double totalRate;

    @Getter @Setter
    Date validityDateFrom;

    @Getter @Setter
    Date validityDateTo;

    @Getter @Setter
    Long createdById;

    @Getter @Setter
    Date createdDate;

    @Getter @Setter
    Long modifiedById;

    @Getter @Setter
    Date modifiedDate;

    @Getter @Setter
    boolean status;

    @Getter
    @Setter
    PaddyGradeDto paddyGradeDto;*/


}
