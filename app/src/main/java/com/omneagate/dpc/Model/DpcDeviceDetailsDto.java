package com.omneagate.dpc.Model;

import lombok.Data;

@Data
public class DpcDeviceDetailsDto {

    String userName;

    LoginDto login_dto;

    String bootLoader;

    String brand;

    String board;

    String display;

    String fingerprint;

    String hardware;

    String host;

    String wifiMac;

    String blueToothMac;

    String androidId;

    String device;

    String manufacturer;

    String deviceName;

    String product;

    String radio;

    String serial;

    String tags;

    String type;

    String serialNumber;

    String androidVersion;

    int sdkVersion;

    String memory;

    String screenResolution;

    String imeiNo;

    String versionName;

    int versionCode;

    String firstInstallTime;

    String lastUpdateTime;

    String cpuSpeed;
}
