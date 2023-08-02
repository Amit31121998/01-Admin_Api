package com.amit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amit.entity.PlanEntity;

public interface PlanRepo  extends JpaRepository<PlanEntity, Integer>{
	
	@Query("update PlanEntity set accStatus=:status where userId=:userId")
	public Integer changePStatus(Integer userId, String status);

}
