package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.FxSwap;

@Repository
public interface FxSwapRepo extends JpaRepository<FxSwap, Integer> {

}
