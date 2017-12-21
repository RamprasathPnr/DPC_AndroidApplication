package com.omneagate.dpc.Model;

import com.omneagate.dpc.Model.EnumModel.ApplicationType;

import lombok.Data;

/**
 * Created by user on 12/7/16.
 */
@Data
public class LoginDto extends ResponseDto {


    String userName;

    String password;

    String deviceId;

    ApplicationType appType;





}
