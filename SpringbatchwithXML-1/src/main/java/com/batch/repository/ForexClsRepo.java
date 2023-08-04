package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.ForexCls;

@Repository
public interface ForexClsRepo extends JpaRepository<ForexCls, Integer> {

}
