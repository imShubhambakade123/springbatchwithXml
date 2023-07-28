//package com.batch.writer;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.batch.model.Employee;
//import com.batch.repository.EmployeeRepo;
//
//public class EmployeeItemWriter implements ItemWriter<Employee> {
//
//	private static final Logger log = LoggerFactory.getLogger(EmployeeItemWriter.class);
//	
//	@Autowired 
//	EmployeeRepo employeeRepo;
//	
//	
//	
//	public EmployeeItemWriter(EmployeeRepo employeeRepo) {
//		super();
//		this.employeeRepo = employeeRepo;
//	}
//
//
//
//	@Override
//	public void write(List<? extends Employee> list) throws Exception {
//		
//      employeeRepo.saveAll(list);
//	}
//
//}
