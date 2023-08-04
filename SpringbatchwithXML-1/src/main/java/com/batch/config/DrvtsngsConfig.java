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

import com.batch.model.Drvtsngs;
import com.batch.processor.DrvtsngsProcessor;
import com.batch.repository.DrvtsngsRepo;

@Configuration
@EnableBatchProcessing
public class DrvtsngsConfig {

	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     DrvtsngsRepo drvtsngsRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<Drvtsngs> drvtsngsXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<Drvtsngs> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(drvtsngsXmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller drvtsngsXmlItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(Drvtsngs.class);
		return unmarshaller;
	}
    
    @Bean
    public DrvtsngsProcessor drvtsngsProcessor() {
    	return new DrvtsngsProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<Drvtsngs> drvtsngsItemWriter(){
    	RepositoryItemWriter<Drvtsngs> writer = new RepositoryItemWriter<>();
    	writer.setRepository(drvtsngsRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step drvtsngsXmlStep(StaxEventItemReader<Drvtsngs> drvtsngsXmlItemReader) {
    	return stepBuilderFactory.get("step-drvtsngs-xml")
    			.<Drvtsngs,Drvtsngs>chunk(10)
    			.reader(drvtsngsXmlItemReader)
    			.processor(drvtsngsProcessor())
    			.writer(drvtsngsItemWriter())
    			.build();
    }
    
    @Bean
    public Job drvtsngsXmlJob(StaxEventItemReader<Drvtsngs> itemReader) {
    	return jobBuilderFactory.get("job-drvtsngs-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(drvtsngsXmlStep(itemReader))
    			.end()
    			.build();
    }
}
