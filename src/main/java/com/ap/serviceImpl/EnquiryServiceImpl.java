package com.ap.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ap.binding.DashboardResponse;
import com.ap.binding.EnquiryForm;
import com.ap.binding.EnquirySearchCriteria;
import com.ap.entity.CourseEntity;
import com.ap.entity.EnqStatusEntity;
import com.ap.entity.StudentEnqEntity;
import com.ap.entity.UserDtlsEntity;
import com.ap.repo.CourseRepo;
import com.ap.repo.EnqStatusRepo;
import com.ap.repo.StudentEnqRepo;
import com.ap.repo.UserDtlsRepo;
import com.ap.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private StudentEnqRepo studentEnqRepo;
	
	@Autowired
	private EnqStatusRepo enqStatusRepo; 

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private HttpSession session;
	
	@Override
	public List<String> getCourses() {

		List<CourseEntity> findAll = courseRepo.findAll();

		List<String> courses = new ArrayList<>();
		for (CourseEntity c : findAll) {
			courses.add(c.getCourseName());
		}
		return courses;
	}

	@Override
	public List<String> getEnquiryStatus() {
		
		List<EnqStatusEntity> findAll = enqStatusRepo.findAll();
		List<String> enqStatus = new ArrayList<>();

		for(EnqStatusEntity e:findAll) {
			enqStatus.add(e.getStatusName());
		}
		
		return enqStatus;
	}

	@Override
	public DashboardResponse getDashboardResponse(Integer userId) {

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		DashboardResponse dashboardResponse = new DashboardResponse();
		
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();

			int totalEnq = enquiries.size();

			long enrolledcnt = enquiries.stream().filter(e -> e.getEnqStatus().equalsIgnoreCase("enrolled")).count();

			long lostCnt = enquiries.stream().filter(e -> e.getEnqStatus().equalsIgnoreCase("lost")).count();

			dashboardResponse.setEnrolledCnt(enrolledcnt);
			dashboardResponse.setLostCnt(lostCnt);
			dashboardResponse.setTotalEnquiresCnt(totalEnq);

		}

		return dashboardResponse;
	}

	@Override
	public List<EnquiryForm> getResponse(Integer userId, EnquirySearchCriteria searchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean upsertEnquiry(EnquiryForm enquiryForm) {
		
		StudentEnqEntity entity=new StudentEnqEntity();
		BeanUtils.copyProperties(enquiryForm, entity);
		
		Integer userId=(Integer) session.getAttribute("userId");
		
		Optional<UserDtlsEntity> user = userDtlsRepo.findById(userId);
		if(user.isPresent()) {
			entity.setUser(user.get());
		}
		studentEnqRepo.save(entity);
		
		return true;
	}

	@Override
	public EnquiryForm getEnquiryForm(Integer enqId) {
		// TODO Auto-generated method stub
		return null;
	}

}
