package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.Securities;

@Repository
public interface SecuritiesRepo extends JpaRepository<Securities, Integer> {

}
