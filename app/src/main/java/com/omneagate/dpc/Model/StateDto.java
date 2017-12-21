package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 22/7/16.
 */
@Data
public class StateDto implements Serializable {


    String statusCode;
    String trackId;
    String errorDescription;
    String id;
    String name;
    String code;
    String status;
    String createdById;
    String createdDate;
    String modifiedById;
    String modifiedDate;

  /*  "statusCode": 2000,
            "trackId": null,
            "errorDescription": null,
            "id": 18,
            "name": "Tamilnadu",
            "code": "A01",
            "status": true,
            "createdById": null,
            "createdDate": null,
            "modifiedById": null,
            "modifiedDate": null,
            "lstateName": "தமிழ்நாடு",
            "districts": null*/
}
