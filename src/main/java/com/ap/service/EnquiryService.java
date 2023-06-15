package com.ap.service;

import java.util.List;

import com.ap.binding.DashboardResponse;
import com.ap.binding.EnquiryForm;
import com.ap.binding.EnquirySearchCriteria;

public interface EnquiryService {

	public List<String> getCourses();

	public List<String> getEnquiryStatus();

	public DashboardResponse getDashboardResponse(Integer userId);

	public List<EnquiryForm> getResponse(Integer userId, EnquirySearchCriteria searchCriteria);

	public boolean upsertEnquiry(EnquiryForm enquiryForm);

	public EnquiryForm getEnquiryForm(Integer enqId);

}
