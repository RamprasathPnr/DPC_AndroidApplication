package com.omneagate.dpc.Model;

import lombok.Data;

@Data
public class BaseResponseDto {
    String statusCode;
    String authenticationStatus;
    String trackId;
//    String deviceStatus;
    String errorDescription;
    String sessionid;


//    RationCardRequestDto beneficiaryDto;
//    DpcUserLoginResponseDto dpcLoginResponseDto;


}