package com.xmlbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.xmlbatch.model.Employee;
import com.xmlbatch.processor.EmployeeProcessor;
import com.xmlbatch.repo.EmployeeRepo;

@Configuration
@EnableBatchProcessing
public class EmployeeConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	
	@Bean
	public ItemReader<Employee> employeeItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		Jaxb2Marshaller empMarshaller = new Jaxb2Marshaller();
		empMarshaller.setClassesToBeBound(Employee.class);
		StaxEventItemReader<Employee> reader = new StaxEventItemReader<>();
        reader.setName("employeeReader");
        reader.setResource(new FileSystemResource(fileName));
        reader.setUnmarshaller(empMarshaller);
        return reader;
	}

    @Bean
	public EmployeeProcessor employeeProcessor() {
		return new EmployeeProcessor();
	}

    @Bean
    public ItemWriter<Employee> employeeItemWriter(){
    	RepositoryItemWriter<Employee> writer = new RepositoryItemWriter<>();
    	writer.setRepository(employeeRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step employeeStep(ItemReader<Employee> employeeItemReader) {
    	return stepBuilderFactory.get("step-employee-batch")
    			.<Employee,Employee>chunk(10)
    			.reader(employeeItemReader)
    			.processor(employeeProcessor())
    			.writer(employeeItemWriter())
    			.build();
    }
    
    @Bean
    public Job employeeJob(ItemReader<Employee> itemReader) {
    	Step employeeStep = employeeStep();
    	
    	StepFlowBuilder<Step> stepFlowBuilder = new StepFlowBuilder("stepflowbuilder");
    	stepFlowBuilder.start(employeeStep);
    	
    	return jobBuilderFactory.get("job-employee")
    			.incrementer(new RunIdIncrementer())
    			.flow(employeeStep(itemReader))
    			.end()
    			.build();
    }
}
