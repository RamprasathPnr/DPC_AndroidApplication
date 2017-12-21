package com.omneagate.dpc.Model;


import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 21/7/16.
 */
@Data
public class BankDto implements Serializable {

	long id;
	String bankName;
	Long code;
	String lname;
	String branch;
	DistrictDto districtDto;
	TalukDto talukDto;
	String contactPerson;
	String mobileNo;
	boolean status;
	long createdBy;
	long createdDate;
	long modifiedBy;
	long modifiedDate;
}
