package com.ap.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ap.binding.DashboardResponse;
import com.ap.binding.EnquiryForm;
import com.ap.service.EnquiryService;

@Controller
public class EnquiryController {
	
	@Autowired
	private EnquiryService enquiryService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String viewDashboard(Model model) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		DashboardResponse dashboardResponse = enquiryService.getDashboardResponse(userId);
		model.addAttribute("dashboard",dashboardResponse);
		
		return "dashboard";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiry(Model model) {
		
		List<String> courses = enquiryService.getCourses();
		List<String> enquiryStatus = enquiryService.getEnquiryStatus();
		EnquiryForm enquiryForm=new EnquiryForm();
		
		model.addAttribute("courses", courses);
		model.addAttribute("enquiryStatus", enquiryStatus);
		model.addAttribute("enquiryForm", enquiryForm);
		
		
		
		return "add-enquiry";
	}
	
	@PostMapping("/enquiry")
	public String addEnq(@ModelAttribute("enquiryForm") EnquiryForm enquiryForm,Model model) {
			
		boolean status = enquiryService.upsertEnquiry(enquiryForm);
		if(status) {
			model.addAttribute("succMsg", "Record added ..!!");
		}else {
			model.addAttribute("errMsg", "Problem Occured..!!");
	
		}
		
		return "add-enquiry";
	}
	
	@GetMapping("/enquires")
	public String viewEnquiry() {
		return "view-enquiries";
	}

}
