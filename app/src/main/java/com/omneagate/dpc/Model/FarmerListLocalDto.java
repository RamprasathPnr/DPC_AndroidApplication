package com.omneagate.dpc.Model;

import lombok.Data;

/**
 * Created by user on 8/8/16.
 */
@Data
public class FarmerListLocalDto {

    long id;
    long id_crop;
    String reference_number;
    String farmer_name;
    String mobileNumber;
    String croupNme;
    String area;
    long landType;
    String aadhar;
    //    String tot;
    double acc;
    double nonacc;
    String rationCardNumber;
    Long bank_id;
    BankDto bankDto;
    String landCount;

}
