package com.ap.service;

import com.ap.binding.LoginForm;
import com.ap.binding.SignUpForm;
import com.ap.binding.UnlockForm;

public interface UserService {
	
	public String login(LoginForm loginForm);
	
	public boolean signUp(SignUpForm signUpForm);
	
	public boolean unlockAccount(UnlockForm unlockForm);
	
	public String forgotPwd(String email);

}
