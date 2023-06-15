package com.ap.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ap.entity.CourseEntity;

@Repository
public interface CourseRepo extends JpaRepository<CourseEntity, Integer>  {

}
