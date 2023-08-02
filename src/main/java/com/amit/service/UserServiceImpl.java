package com.amit.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.binding.DasboardCards;
import com.amit.binding.LoginForm;
import com.amit.binding.UserAccForm;
import com.amit.constants.AppConstants;
import com.amit.entity.EligEntity;
import com.amit.entity.UserEntity;
import com.amit.repo.EligRepo;
import com.amit.repo.PlanRepo;
import com.amit.repo.UserRepo;
import com.amit.utlity.EmailUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private PlanRepo planRepo;

	@Autowired
	private EligRepo eligRepo;

	@Override
	public String login(LoginForm loginForm) {

		UserEntity entity = userRepo.findByEmailAndPwd(loginForm.getEmail(), loginForm.getPwd());
		if (entity == null) {
			return AppConstants.INVALID_CRED;
		}
		if ("Y".equals(entity.getActiveSw()) && AppConstants.UNLOCKED.equals(entity.getAccStatus())) {
			return AppConstants.SUCCESS;
		} else {
			return AppConstants.ACCOUNT_LOCKED;
		}
	}

	@Override
	public boolean recoverPwd(String email) {

		UserEntity entity = userRepo.findByEmail(email);
		if (entity == null) {
			return false;
		} else {
			String subject = "";
			String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", entity);
			return emailUtils.sendEmail(subject, body, email);
		}
	}

	@Override
	public DasboardCards fetchDashboardInfo() {

		 planRepo.count();

		List<EligEntity> eligList = eligRepo.findAll();

		Long approvedCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals(AppConstants.AP)).count();

		Long deniedCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals(AppConstants.DN)).count();

		Double total = eligList.stream().mapToDouble(EligEntity::getBenefitAmt).sum();


		DasboardCards card = new DasboardCards();

		card.setApprovedCnt(approvedCnt);
		card.setDeniedCnt(deniedCnt);
		card.setBeniftAmtGiven(total);

		return card;
	}

	@Override
	public UserAccForm getUserByEmail(String email) {
		UserEntity findByEmail = userRepo.findByEmail(email);
		UserAccForm user = new UserAccForm();
		BeanUtils.copyProperties(findByEmail, user);
		return user;
	}
	
	private String readEmailBody(String filename, UserEntity user) {
		StringBuilder sb = new StringBuilder();
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			lines.forEach(line -> {
				line = line.replace(AppConstants.FNAME, user.getFullName());
				line = line.replace(AppConstants.PWD, user.getPwd());
				line = line.replace(AppConstants.EMAIL, user.getEmail());
				sb.append(line);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
