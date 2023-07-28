package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Employee;

public class Employeeprocessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee employee) throws Exception {
		
		return employee;
	}

}
