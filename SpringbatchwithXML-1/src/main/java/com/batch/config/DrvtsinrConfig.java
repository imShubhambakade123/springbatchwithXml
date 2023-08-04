package com.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.batch.model.Drvtsinr;
import com.batch.processor.DrvtsinrProcessor;
import com.batch.repository.DrvtsinrRepo;

@Configuration
@EnableBatchProcessing
public class DrvtsinrConfig {

	
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     DrvtsinrRepo drvtsinrRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<Drvtsinr> drvtsinrXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<Drvtsinr> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(drvtsinrXmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller drvtsinrXmlItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(Drvtsinr.class);
		return unmarshaller;
	}
    
    @Bean
    public DrvtsinrProcessor drvtsinrItemprocessor() {
    	return new DrvtsinrProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<Drvtsinr> drvtsinrItemWriter(){
    	RepositoryItemWriter<Drvtsinr> writer = new RepositoryItemWriter<>();
    	writer.setRepository(drvtsinrRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step drvtsinrXmlStep(StaxEventItemReader<Drvtsinr> drvtsinrXmlItemReader) {
    	return stepBuilderFactory.get("step-drvtsinr-xml")
    			.<Drvtsinr,Drvtsinr>chunk(10)
    			.reader(drvtsinrXmlItemReader)
    			.processor(drvtsinrItemprocessor())
    			.writer(drvtsinrItemWriter())
    			.build();
    }
    
    @Bean
    public Job drvtsinrXmlJob(StaxEventItemReader<Drvtsinr> itemReader) {
    	return jobBuilderFactory.get("job-drvtsinr-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(drvtsinrXmlStep(itemReader))
    			.end()
    			.build();
    }
}
