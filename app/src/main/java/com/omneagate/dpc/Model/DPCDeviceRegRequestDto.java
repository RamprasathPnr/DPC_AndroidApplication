package com.omneagate.dpc.Model;

import lombok.Data;

/**
 * Created by user on 12/7/16.
 */
@Data
public class DPCDeviceRegRequestDto {

    LoginDto loginDto;

    /** device details*/

    DpcDeviceDetailsDto dpcDeviceDetailsDto;
}
