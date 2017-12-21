package com.omneagate.dpc.Model;

import lombok.Data;

/**
 * Created by user on 28/7/16.
 */
@Data
public class DpcUserLoginResponseDto {
    boolean authenticationStatus; // Status of Authentication - true - Authenticated , false - not authenticated

    String  deviceStatus;	// status of device associated with user

    String sessionid; // Session id only if authentication is successful.

    UserDetailDto userDetailDto;


}
