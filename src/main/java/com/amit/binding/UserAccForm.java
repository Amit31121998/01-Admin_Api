package com.amit.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserAccForm {

	private String fullName;
    private String email;
    private Long mobileNo;
    private String gender;
    private LocalDate dob;
    private Long ssn;
    private String activeSw;
    private Integer roleId;
}
