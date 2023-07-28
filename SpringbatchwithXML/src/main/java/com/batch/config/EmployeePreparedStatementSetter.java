package com.batch.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.batch.model.Employee;

public class EmployeePreparedStatementSetter implements ItemPreparedStatementSetter<Employee> {

	@Override
	public void setValues(Employee employee, PreparedStatement ps) throws SQLException {
		ps.setInt(1, employee.getId());
		ps.setString(2, employee.getFirstName());
		ps.setString(3, employee.getLastName());
		ps.setString(4, employee.getEmailAddress());
		ps.setString(5, employee.getUsername());
		ps.setString(6, employee.getDomain());
	}

}
