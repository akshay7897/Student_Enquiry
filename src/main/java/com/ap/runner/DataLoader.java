package com.ap.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ap.entity.CourseEntity;
import com.ap.entity.EnqStatusEntity;
import com.ap.repo.CourseRepo;
import com.ap.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo enqStatusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();
		
		CourseEntity c1=new CourseEntity();
		c1.setCourseName("Java");
		
		CourseEntity c2=new CourseEntity();
		c2.setCourseName("Python");
		
		
		CourseEntity c3=new CourseEntity();
		c3.setCourseName("Php");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		
		enqStatusRepo.deleteAll();
		
		EnqStatusEntity s1=new EnqStatusEntity();
		s1.setStatusName("new");
		
		EnqStatusEntity s2=new EnqStatusEntity();
		s2.setStatusName("enrolled");
		
		EnqStatusEntity s3=new EnqStatusEntity();
		s3.setStatusName("lost");
		
		enqStatusRepo.saveAll(Arrays.asList(s1,s2,s3));
		
		
	
		
		
		
	}

}
