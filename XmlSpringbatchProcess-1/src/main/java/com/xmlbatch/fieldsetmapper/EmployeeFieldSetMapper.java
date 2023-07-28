package com.xmlbatch.fieldsetmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.xmlbatch.model.Employee;

public class EmployeeFieldSetMapper implements FieldSetMapper<Employee>{

	@Override
	public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
		Employee employee = new Employee();
		
		employee.setName(fieldSet.readString(1));
		employee.setAge(fieldSet.readInt(2));
		employee.setDesignation(fieldSet.readString(3));
		return employee;
	}

}
