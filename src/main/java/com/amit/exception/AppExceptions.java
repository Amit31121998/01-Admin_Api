package com.amit.exception;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AppExceptions {

	private String exCode;
	
	private String exDesc;
	
	private LocalDate exDate;
}
