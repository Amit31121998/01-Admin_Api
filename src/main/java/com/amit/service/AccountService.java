package com.amit.service;

import java.util.List;

import com.amit.binding.UnlockAccForm;
import com.amit.binding.UserAccForm;

public interface AccountService {
	
	 public boolean createUserAccount(UserAccForm accForm);

	    public List<UserAccForm> fetchUserAccounts( );

	    public UserAccForm getUserAccById(Integer accId);

	    public String changeAccStatus(Integer accId, String status);

	    public String unlockUserAccount(UnlockAccForm unlockAccForm);


}
