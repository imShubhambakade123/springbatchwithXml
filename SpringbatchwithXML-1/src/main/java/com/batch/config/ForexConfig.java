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

import com.batch.model.Forex;
import com.batch.processor.ForexProcessor;
import com.batch.repository.ForexRepo;

@Configuration
@EnableBatchProcessing
public class ForexConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     ForexRepo forexRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<Forex> forexXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<Forex> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(forexXmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller forexXmlItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(Forex.class);
		return unmarshaller;
	}
    
    @Bean
    public ForexProcessor forexItemprocessor() {
    	return new ForexProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<Forex> forexItemWriter(){
    	RepositoryItemWriter<Forex> writer = new RepositoryItemWriter<>();
    	writer.setRepository(forexRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step forexXmlStep(StaxEventItemReader<Forex> forexXmlItemReader) {
    	return stepBuilderFactory.get("step-forex-xml")
    			.<Forex,Forex>chunk(10)
    			.reader(forexXmlItemReader)
    			.processor(forexItemprocessor())
    			.writer(forexItemWriter())
    			.build();
    }
    
    @Bean
    public Job forexXmlJob(StaxEventItemReader<Forex> itemReader) {
    	return jobBuilderFactory.get("job-forex-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(forexXmlStep(itemReader))
    			.end()
    			.build();
    }
}
