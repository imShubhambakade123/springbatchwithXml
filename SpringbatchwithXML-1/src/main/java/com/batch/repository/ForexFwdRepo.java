package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.ForexFwd;

@Repository
public interface ForexFwdRepo extends JpaRepository<ForexFwd, Integer> {

}
