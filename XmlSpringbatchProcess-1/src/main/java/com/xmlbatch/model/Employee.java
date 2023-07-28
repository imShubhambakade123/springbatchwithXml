package com.xmlbatch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
	@SequenceGenerator(name = "employee_seq",sequenceName = "employee_seq",allocationSize = 1)
	@Column(name = "Emp_Id",nullable = false)
	private Long id;
	
	@Column(name = "Emp_Name")
	private String name;
	
	@Column(name = "Emp_Age")
	private int age;
	
	@Column(name = "Emp_Designation")
	private String designation;
	
	
}
