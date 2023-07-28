package com.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Employee;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee employee) throws Exception {
		String emailAddress = employee.getEmailAddress();
		
		if (emailAddress != null) {
            int atIndex = emailAddress.indexOf('@');
            if (atIndex >= 0) {
                employee.setUsername(emailAddress.substring(0, atIndex));
                employee.setDomain(emailAddress.substring(atIndex + 1));
            }
		}   
		return employee;
	}
}
