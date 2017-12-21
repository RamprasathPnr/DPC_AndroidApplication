package com.omneagate.dpc.Model;

/**
 * Created by Shanthakumar on 26-08-2016.
 */
import lombok.Data;

@Data
public class LoginRequest {

    public String userName;
    public String password;
    public String deviceId;
}
