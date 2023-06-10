package com.ap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnquiryController {
	
	@GetMapping("/dashboard")
	public String viewDashboard() {
		return "dashboard";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiry() {
		return "add-enquiry";
	}
	
	@GetMapping("/enquires")
	public String viewEnquiry() {
		return "view-enquiries";
	}

}
