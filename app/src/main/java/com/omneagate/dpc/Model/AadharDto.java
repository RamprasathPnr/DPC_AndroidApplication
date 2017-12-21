package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

@Data
public class AadharDto extends BaseResponseDto implements Serializable {

    long id;

    long aadhaarNum;

    long createdDate;

    String uid;

    String name;

    Character gender;

    Long yob;

    String co;

    String house;

    String street;

    String lm;

    String loc;

    String vtc;

    String po;

    String dist;

    String subdist;

    String state;

    String pc;

    Long dob;

}
