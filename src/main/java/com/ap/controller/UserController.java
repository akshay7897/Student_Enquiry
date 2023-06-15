package com.ap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ap.binding.LoginForm;
import com.ap.binding.SignUpForm;
import com.ap.binding.UnlockForm;
import com.ap.service.UserService;

@Controller
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String signUp(Model model) {
		logger.info("signup method executing.");

		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignUp(SignUpForm signForm, Model model) {

		boolean signUp = userService.signUp(signForm);
		if (signUp) {
			model.addAttribute("success", "Account created ,Check your mail");
			model.addAttribute("user", signForm);

		} else {
			model.addAttribute("error", "problem occured");
			model.addAttribute("user", signForm);

		}
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm unlockForm = new UnlockForm();
		unlockForm.setEmail(email);

		model.addAttribute("unlockForm", unlockForm);

		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlock(@ModelAttribute("unlockForm") UnlockForm unlockForm, Model model) {

		if (unlockForm.getNewPwd().equals(unlockForm.getConfirmPwd())) {

			boolean status = userService.unlockAccount(unlockForm);
			if (status) {
				model.addAttribute("succMsg", "Your Account is Unlocked");
			} else {
				model.addAttribute("errMsg", "Please enter correct Temporary password");

			}

		} else {
			model.addAttribute("errMsg", "New password and Confirm password Should be same");
		}

		return "unlock";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("form", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("form") LoginForm form, Model model) {

		boolean status = userService.login(form);
		if (status) {
			return "redirect:/dashboard";
		} else {
			model.addAttribute("errmsg", "Your credentials are wrong");
		}

		return "login";
	}

	@GetMapping("/forgot")
	public String forgotPwd() {
		return "forgotPwd";
	}
	

	@PostMapping("/forgot")
	public String forgot(@RequestParam String email,Model model) {
		
		boolean status = userService.forgotPwd(email);
		if(status) {
			model.addAttribute("succMsg", "Password sent to your mail");
		}else {
			model.addAttribute("errMsg", "Email is Invalid.");

		}
		
		return "forgotPwd";
	}


}
