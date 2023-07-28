package com.xmlbatch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xmlbatch.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
