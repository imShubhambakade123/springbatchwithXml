package com.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.batch.model.Employee;
import com.batch.processor.Employeeprocessor;
import com.batch.repository.EmployeeRepository;

@Configuration
@EnableBatchProcessing
public class EmployeeConfig {
    
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	EmployeeRepository employeeRepo;
	
	@Bean
	@StepScope
	public ItemReader<Employee> xmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<Employee> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("employee");
		reader.setUnmarshaller(xmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller xmlItemMarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Employee.class);
		return marshaller;
	}
    
    @Bean
    public Employeeprocessor employeeprocessor() {
    	return new Employeeprocessor();
    }
    
    @Bean
    public RepositoryItemWriter<Employee> employeeItemWriter(){
    	RepositoryItemWriter<Employee> writer = new RepositoryItemWriter<>();
    	writer.setRepository(employeeRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step employeeXmlStep(ItemReader<Employee> emplItemReader) {
    	return stepBuilderFactory.get("step-employee-xml")
    			.<Employee,Employee>chunk(10)
    			.reader(emplItemReader)
    			.processor(employeeprocessor())
    			.writer(employeeItemWriter())
    			.build();
    }
    
    @Bean
    public Job employeeXmlJob(ItemReader<Employee> itemReader) {
    	return jobBuilderFactory.get("job-employee-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(employeeXmlStep(itemReader))
    			.end()
    			.build();
    }
    
}
