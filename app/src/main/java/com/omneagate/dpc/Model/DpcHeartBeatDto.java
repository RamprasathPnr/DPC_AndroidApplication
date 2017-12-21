package com.omneagate.dpc.Model;

import lombok.Data;

@Data
public class DpcHeartBeatDto extends BaseResponseDto{



    long id;

    /** fps store id */

    long dpcProfileId;

    /** current battery level */

    int  batteryLevel;


    String latitude;


    String longitude;


    int version;


   /* String dpcId; //Dpc shop identifier and same will be echoed back to FPS device from the server
    int batteryLevel;
    String latitude;
    String longtitude;
    int versionNum;*/

}
