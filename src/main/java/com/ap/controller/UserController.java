package com.ap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ap.binding.SignUpForm;
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
			model.addAttribute("success", "Check your mail");
			model.addAttribute("user", signForm);
			
		} else {
			model.addAttribute("error", "problem occured");
			model.addAttribute("user", signForm);

		}
		return "signup";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/unlock")
	public String unlockPage() {
		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgotPwd() {
		return "forgotPwd";
	}

}
