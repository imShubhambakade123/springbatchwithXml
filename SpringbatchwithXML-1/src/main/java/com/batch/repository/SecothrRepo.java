package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.Secothr;

@Repository
public interface SecothrRepo extends JpaRepository<Secothr, Integer> {

}
