package com.omneagate.dpc.Model;


import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 21/7/16.
 */
@Data
public class LandTypeDto implements Serializable {
    /*long id;
    String name;
    long createdBy;
    long createdDate;
    long modifiedBy;
    long modifiedDate;*/
    long id;

    String name;

    long createdBy;

    long createdDate;

    long modifiedBy;

    long modifiedDate;

    boolean status;
}
