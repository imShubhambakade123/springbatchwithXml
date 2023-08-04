package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.Drvtsinr;

@Repository
public interface DrvtsinrRepo extends JpaRepository<Drvtsinr, Integer> {

}
