package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.DrvtsgsMifor;

@Repository
public interface DrvtsgsMiforRepo extends JpaRepository<DrvtsgsMifor, Integer> {

}
