package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.Forex;

@Repository
public interface ForexRepo extends JpaRepository<Forex, Integer> {

}
