package com.amit.service;

import com.amit.binding.DasboardCards;
import com.amit.binding.LoginForm;
import com.amit.binding.UserAccForm;

public interface UserService {
	
	public  String login(LoginForm loginForm);

    public boolean recoverPwd(String email);

    public DasboardCards fetchDashboardInfo();
    
    public UserAccForm getUserByEmail(String email);

}
