package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.CBLO;

@Repository
public interface CBLORepo extends JpaRepository<CBLO, Integer>{

}
