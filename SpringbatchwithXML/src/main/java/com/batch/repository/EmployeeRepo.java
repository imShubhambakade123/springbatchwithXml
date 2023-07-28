package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Long> {

}
