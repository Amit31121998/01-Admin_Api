package com.amit.binding;

import lombok.Data;

@Data
public class DasboardCards {

	private Long plansCnt;
	private Long approvedCnt;
	private Long deniedCnt;
	private Double beniftAmtGiven;

	private UserAccForm user;
}
