 package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.Drvtsngs;

@Repository
public interface DrvtsngsRepo extends JpaRepository<Drvtsngs, Integer> {

}
