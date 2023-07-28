package com.batch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "Lastname")
	private String lastName;
	
	@Column(name = "Designation")
	private String designation;
	
	@Column(name = "Age")
	private int age;
	
	@Column(name = "Salary")
	private double salary;
	
}
