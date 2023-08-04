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

import com.batch.model.DrvtsgsMifor;
import com.batch.processor.DrvtsgsMiforProcessor;
import com.batch.repository.DrvtsgsMiforRepo;

@Configuration
@EnableBatchProcessing
public class DrvtsgsMiforConfig {

	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     DrvtsgsMiforRepo drvtsgsMiforRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<DrvtsgsMifor> drvtsgsMiforXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<DrvtsgsMifor> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(srvtsgsMiforItemMarshaller());
		return reader;
	}

	public Unmarshaller srvtsgsMiforItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(DrvtsgsMifor.class);
		return unmarshaller;
	}
    
    @Bean
    public DrvtsgsMiforProcessor drvtsgsMiforProcessor() {
    	return new DrvtsgsMiforProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<DrvtsgsMifor> drvtsgsMiforItemWriter(){
    	RepositoryItemWriter<DrvtsgsMifor> writer = new RepositoryItemWriter<>();
    	writer.setRepository(drvtsgsMiforRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step drvtsgsMiforXmlStep(StaxEventItemReader<DrvtsgsMifor> drvtsgsMiforXmlItemReader) {
    	return stepBuilderFactory.get("step-drvtsgsMifor-xml")
    			.<DrvtsgsMifor,DrvtsgsMifor>chunk(10)
    			.reader(drvtsgsMiforXmlItemReader)
    			.processor(drvtsgsMiforProcessor())
    			.writer(drvtsgsMiforItemWriter())
    			.build();
    }
    
    @Bean
    public Job drvtsgsMiforXmlJob(StaxEventItemReader<DrvtsgsMifor> itemReader) {
    	return jobBuilderFactory.get("job-drvtsgsMifor-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(drvtsgsMiforXmlStep(itemReader))
    			.end()
    			.build();
    }
}
