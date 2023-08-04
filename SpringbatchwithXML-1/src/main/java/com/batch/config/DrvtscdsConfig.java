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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.batch.model.Drvtscds;
import com.batch.processor.DrvtscdsProcessor;
import com.batch.repository.DrvtscdsRepo;

@Configuration
@EnableBatchProcessing
public class DrvtscdsConfig {

	
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     DrvtscdsRepo drvtscdsRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<Drvtscds> drvtscdsXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<Drvtscds> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(xmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller xmlItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(Drvtscds.class);
		return unmarshaller;
	}
    
    @Bean
    public DrvtscdsProcessor drvtscdsItemprocessor() {
    	return new DrvtscdsProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<Drvtscds> drvtscdsItemWriter(){
    	RepositoryItemWriter<Drvtscds> writer = new RepositoryItemWriter<>();
    	writer.setRepository(drvtscdsRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step drvtscdsXmlStep(StaxEventItemReader<Drvtscds> drvtscdsXmlItemReader) {
    	return stepBuilderFactory.get("step-drvtscds-xml")
    			.<Drvtscds,Drvtscds>chunk(10)
    			.reader(drvtscdsXmlItemReader)
    			.processor(drvtscdsItemprocessor())
    			.writer(drvtscdsItemWriter())
    			.build();
    }
    
    @Bean
    public Job drvtscdsXmlJob(StaxEventItemReader<Drvtscds> itemReader) {
    	return jobBuilderFactory.get("job-drvtscds-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(drvtscdsXmlStep(itemReader))
    			.end()
    			.build();
    }
}
