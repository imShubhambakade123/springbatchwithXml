package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.ForexTr;

@Repository
public interface ForexTrRepo extends JpaRepository<ForexTr, Integer> {

}
