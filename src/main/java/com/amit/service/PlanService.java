package com.amit.service;

import java.util.List;

import com.amit.binding.PlanForm;

public interface PlanService {
	
	public boolean createPlan(PlanForm planForm);

    public List<PlanForm> fetchPlans( );

    public PlanForm getPlanById(Integer planId);

    public String changePlanStatus(Integer planId, String status);

}
