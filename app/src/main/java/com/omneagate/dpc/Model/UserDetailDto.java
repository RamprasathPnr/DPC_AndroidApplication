package com.omneagate.dpc.Model;

import lombok.Data;

/**
 * Created by user on 13/7/16.
 */
@Data
public class UserDetailDto {
    String statusCode;
    String trackId;
    String errorDescription;
    Long id;
    String userId;
    String password;
    String username;
    String profile;
    boolean active;
    long createdDate;
    long modifiedDate;
    long createdBy;
    long modifiedBy;
    String godown_id;
    String keroseneBunkDto;
    String keroseneWholeSalerDto;
    String smsStatus;
    String smsModifiedDate;
    String regionProfileId;
    String contactNumber;
    String emailId;
    String oldPassword;
    String fpsStore;
    DPCProfileDto dpcProfileDto;
    String encryptedPassword;
}
