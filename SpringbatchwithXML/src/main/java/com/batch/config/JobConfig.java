package com.batch.config;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.batch.model.Employee;
import com.batch.processor.EmployeeProcessor;
import com.batch.repository.EmployeeRepo;

@Configuration
@EnableTransactionManagement
@EnableBatchProcessing
public class JobConfig extends DefaultBatchConfigurer {
    
	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Autowired
	DataSource dataSource;

	@Autowired
	EmployeeRepo employeeRepo;
	
	
	@Bean
	@StepScope
	public StaxEventItemReader<Employee> employeeItemReader(){
		StaxEventItemReader<Employee> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource("E:/CCILSendExcel/employee.xml"));
		reader.setFragmentRootElementName("employee");
				
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Employee.class);
		reader.setUnmarshaller(marshaller);
		return reader;	
	}

	@Bean
	public EmployeeProcessor employeeProcessor() {
		return new EmployeeProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Employee> employeeItemWriter(){
		JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(dataSource);
		writer.setSql("Insert into employee(id,firstName,lastName,emailAddress,username,domain) values(?,?,?,?,?,?)");
		writer.setItemPreparedStatementSetter(new EmployeePreparedStatementSetter());
		return writer;
	}
	
	
//	@Bean
//	public ItemWriter<Employee> employeeItemWriter(){
//		RepositoryItemWriter<Employee> writer = new RepositoryItemWriter<>();
//		writer.setRepository(employeeRepo);
//		writer.setMethodName("save");
//		return writer;
//	}
	
	
	@Bean
	public Step employeeXmlStep() {
		return stepBuilderFactory.get("step-employee-xml")
				.<Employee, Employee>chunk(10)
				.reader(employeeItemReader())
				.processor(employeeProcessor())
				.writer(employeeItemWriter())
				.build();
	}

	@Bean
	public Job employeeXmljob() {
		return jobBuilderFactory.get("job-employee-xml")
				.incrementer(new RunIdIncrementer())
				.flow(employeeXmlStep())
				.end()
				.build();

	}
}
