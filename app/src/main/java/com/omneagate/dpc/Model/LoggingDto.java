package com.omneagate.dpc.Model;

import java.util.Date;

import lombok.Data;

/**
 * Created by user on 27/6/16.
 */
@Data
public class LoggingDto {

    long id;                        //Primary Key value

    String deviceId;                    //device idgmt

    String logMessage;                //error message

    String errorType;                //type of error- could be FATAL, WARN

    Date createDate;
}
