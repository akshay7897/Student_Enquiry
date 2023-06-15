package com.ap.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ap.entity.EnqStatusEntity;

@Repository
public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
