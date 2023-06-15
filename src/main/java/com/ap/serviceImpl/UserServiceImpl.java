package com.ap.serviceImpl;

import javax.servlet.http.HttpSession;

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

	@Autowired
	private HttpSession session;

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
	public boolean login(LoginForm loginForm) {

		UserDtlsEntity entity = repo.findByEmailAndPwd(loginForm.getUsername(), loginForm.getPwd());

		if (entity != null && entity.getAccountStatus().equals("Unlocked")) {

			session.setAttribute("userId", entity.getUserId());

			return true;
		}
		return false;
	}

	@Override
	public boolean unlockAccount(UnlockForm unlockForm) {

		UserDtlsEntity entity = repo.findByEmail(unlockForm.getEmail());
		if (entity != null && entity.getPwd().equals(unlockForm.getTempPwd())) {
			entity.setPwd(unlockForm.getConfirmPwd());
			entity.setAccountStatus("Unlocked");
			repo.save(entity);
			return true;
		}

		return false;
	}

	@Override
	public boolean forgotPwd(String email) {

		UserDtlsEntity entity = repo.findByEmail(email);

		if (entity == null) {
			return false;
		}

		String subject = "Recover your password";
		String body = "<h4>Your password :: </h4>" + entity.getPwd();

		emailutils.sendEmail(email, subject, body);

		return true;
	}

}
