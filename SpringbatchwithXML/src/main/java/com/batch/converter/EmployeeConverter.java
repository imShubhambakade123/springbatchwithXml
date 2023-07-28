//package com.batch.converter;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//import com.batch.model.Employee;
//import com.thoughtworks.xstream.converters.Converter;
//import com.thoughtworks.xstream.converters.MarshallingContext;
//import com.thoughtworks.xstream.converters.UnmarshallingContext;
//import com.thoughtworks.xstream.io.HierarchicalStreamReader;
//import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
//
//public class EmployeeConverter implements Converter {
//
//	private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//	
//	@Override
//	public boolean canConvert(Class type) {
//		
//		return type.equals(Employee.class);
//	}
//
//	@Override
//	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
//		reader.moveDown();
//		Employee employee = new Employee();
//		employee.setId(Integer.valueOf(reader.getValue()));
//		
//		reader.moveUp();
//		reader.moveDown();
//		employee.setFirstName(String.valueOf(reader.getValue()));
//		
//		reader.moveUp();
//		reader.moveDown();
//		employee.setLastName(String.valueOf(reader.getValue()));
//		
//		reader.moveUp();
//		reader.moveDown();
//		employee.setBithDate(LocalDateTime.parse(reader.getValue(), DT_FORMATTER));
//		return employee;
//	}
//
//}
