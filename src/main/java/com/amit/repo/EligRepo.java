package com.amit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.entity.EligEntity;

public interface EligRepo extends JpaRepository<EligEntity, Integer> {

}
