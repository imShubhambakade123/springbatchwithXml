package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.DrvtsgsMibor;

@Repository
public interface DrvtsgsMiborRepo extends JpaRepository<DrvtsgsMibor, Integer> {

}
