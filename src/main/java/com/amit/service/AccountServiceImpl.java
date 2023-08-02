package com.amit.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.binding.UnlockAccForm;
import com.amit.binding.UserAccForm;
import com.amit.constants.AppConstants;
import com.amit.entity.UserEntity;
import com.amit.repo.UserRepo;
import com.amit.utlity.EmailUtils;

@Service
public class AccountServiceImpl implements AccountService {
	
	private Logger logger=LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public boolean createUserAccount(UserAccForm accForm) {

		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(accForm, entity);

		// set random pwd
		entity.setPwd(generatePwd());

		// set status
		entity.setAccStatus(AppConstants.LOCKED);

		// set switch
		entity.setActiveSw(AppConstants.Y_STR);

		// save entity
		userRepo.save(entity);

		// send email
		String subject = "User Registration";
		String body = readEmailBody("REG_EMAIL_BODY.txt", entity);
		 return emailUtils.sendEmail(subject, body, accForm.getEmail());
		
	}

	@Override
	public List<UserAccForm> fetchUserAccounts() {

		List<UserEntity> findAll = userRepo.findAll();

		List<UserAccForm> users = new ArrayList<>();

		for (UserEntity userEntity : findAll) {
			UserAccForm user = new UserAccForm();

			BeanUtils.copyProperties(userEntity, user);
			users.add(user);
		}

		return users;
	}

	@Override
	public UserAccForm getUserAccById(Integer accId) {

		Optional<UserEntity> optional = userRepo.findById(accId);

		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			UserAccForm user = new UserAccForm();
			BeanUtils.copyProperties(userEntity, user);
			return user;

		}
		return null;
	}

	@Override
	public String changeAccStatus(Integer accId, String status) {

		Integer cn = userRepo.updateAccStatus(accId, status);
		if (cn > 0) {
			return AppConstants.PLAN_STATUS_SUCCESS;
		} else {
			return AppConstants.PLAN_STATUS_FAILD;
		}
	}

	@Override
	public String unlockUserAccount(UnlockAccForm unlockAccForm) {

		UserEntity entity = userRepo.findByEmail(unlockAccForm.getEmail());
		entity.setPwd(unlockAccForm.getNewPwd());
		entity.setAccStatus(AppConstants.UNLOCKED);

		userRepo.save(entity);

		return "Account Unlocked";
	}

	private String generatePwd() {
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";

		// combine all strings
		String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

		// create random string builder
		StringBuilder sb = new StringBuilder();

		// create an object of Random class
		Random random = new Random();

		// specify length of random string
		int length = 6;

		for (int i = 0; i < length; i++) {

			// generate random index number
			int index = random.nextInt(alphaNumeric.length());

			// get character specified by index
			// from the string
			char randomChar = alphaNumeric.charAt(index);

			// append the character to string builder
			sb.append(randomChar);
		}

		return sb.toString();
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
