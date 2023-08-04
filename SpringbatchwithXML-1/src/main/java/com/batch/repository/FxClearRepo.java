package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.FxClear;

@Repository
public interface FxClearRepo extends JpaRepository<FxClear, Integer> {

}
