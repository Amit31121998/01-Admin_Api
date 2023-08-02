package com.amit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.binding.PlanForm;
import com.amit.constants.AppConstants;
import com.amit.entity.PlanEntity;
import com.amit.repo.PlanRepo;

@Service
public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanRepo planRepo;

	@Override
	public boolean createPlan(PlanForm planForm) {

		PlanEntity entity = new PlanEntity();

		BeanUtils.copyProperties(planForm, entity);
		entity.setActiveSw(AppConstants.Y_STR);
		planRepo.save(entity);
		return true;
	}

	@Override
	public List<PlanForm> fetchPlans() {

		List<PlanEntity> planEntity = planRepo.findAll();

		List<PlanForm> plans = new ArrayList<>();
		for (PlanEntity plan : planEntity) {

			PlanForm form = new PlanForm();
			BeanUtils.copyProperties(plan, form);
			plans.add(form);
		}
		return plans;
	}

	@Override
	public PlanForm getPlanById(Integer planId) {
		Optional<PlanEntity> optional = planRepo.findById(planId);
		if (optional.isPresent()) {
			PlanEntity planEntity = optional.get();
			PlanForm plan=new PlanForm();
			BeanUtils.copyProperties(planEntity, plan);
			return plan;
		}
		return null;
	}

	@Override
	public String changePlanStatus(Integer planId, String status) {
		
		Integer cn = planRepo.changePStatus(planId, status);
		if (cn > 0) {
			return AppConstants.PLAN_STATUS_SUCCESS;
		} else {
			return AppConstants.PLAN_STATUS_FAILD;
		}
	}
}
