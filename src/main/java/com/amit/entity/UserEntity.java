package com.amit.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="IES_USERS")
@Setter
@Getter
public class UserEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
	private Integer userId;
	private String fullName;
	private String email;
	private String pwd;
	private String mobileNo;
	private String gender;
	private String dob;
	private Integer ssn;
	private String activeSw ;
	private String accStatus;
	private Integer rollId;
	
	private LocalDate createDate;
	private LocalDate updateDate;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<PlanEntity> plans;
}
