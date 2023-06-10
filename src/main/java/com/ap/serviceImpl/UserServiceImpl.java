package com.ap.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ap.binding.LoginForm;
import com.ap.binding.SignUpForm;
import com.ap.binding.UnlockForm;
import com.ap.entity.UserDtlsEntity;
import com.ap.repo.UserDtlsRepo;
import com.ap.service.UserService;
import com.ap.util.EmailUtils;
import com.ap.util.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo repo;

	@Autowired
	private EmailUtils emailutils;

	@Override
	public boolean signUp(SignUpForm signUpForm) {

		UserDtlsEntity user = repo.findByEmail(signUpForm.getEmail());
		if (user != null) {
			return false;
		}

		// add form date to object

		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(signUpForm, entity);

		// set password as random password

		String pwd = PwdUtils.genrateRandomPwd();
		entity.setPwd(pwd);

		// set status as locked
		entity.setAccountStatus("Locked");

		// insert records into DB
		if (entity.getUsername() != null && entity.getUsername() != "" && entity.getEmail() != null
				&& entity.getEmail() != "" && entity.getPhno() != null) {
			repo.save(entity);
		} else {
			return false;
		}

		String to = signUpForm.getEmail();
		String subject = "Unlock your account";

		StringBuffer body = new StringBuffer();
		body.append("<h3>Use below Temporary password to unlock account</h3>");
		body.append("Temporary password - " + pwd + ".");
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8081/unlock?email=" + to + "\">click here to unlock</a>");

		emailutils.sendEmail(to, subject, body.toString());

		return true;
	}

	@Override
	public String login(LoginForm loginForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean unlockAccount(UnlockForm unlockForm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String forgotPwd(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
