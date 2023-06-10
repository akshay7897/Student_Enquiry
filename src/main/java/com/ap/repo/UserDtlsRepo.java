package com.ap.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ap.entity.UserDtlsEntity;

@Repository
public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer> {
	
	public UserDtlsEntity findByEmail(String email);

}
