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

import com.batch.model.ForexFwd;
import com.batch.processor.ForexFwdProcessor;
import com.batch.repository.ForexFwdRepo;

@Configuration
@EnableBatchProcessing
public class ForexFwdConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     ForexFwdRepo forexFwdRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<ForexFwd> forexFwdXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<ForexFwd> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(forexFwdXmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller forexFwdXmlItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(ForexFwd.class);
		return unmarshaller;
	}
    
    @Bean
    public ForexFwdProcessor forexFwdItemprocessor() {
    	return new ForexFwdProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<ForexFwd> forexFwdItemWriter(){
    	RepositoryItemWriter<ForexFwd> writer = new RepositoryItemWriter<>();
    	writer.setRepository(forexFwdRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step forexFwdXmlStep(StaxEventItemReader<ForexFwd> forexFwdXmlItemReader) {
    	return stepBuilderFactory.get("step-forexFwd-xml")
    			.<ForexFwd,ForexFwd>chunk(10)
    			.reader(forexFwdXmlItemReader)
    			.processor(forexFwdItemprocessor())
    			.writer(forexFwdItemWriter())
    			.build();
    }
    
    @Bean
    public Job forexFwdXmlJob(StaxEventItemReader<ForexFwd> itemReader) {
    	return jobBuilderFactory.get("job-forexFwd-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(forexFwdXmlStep(itemReader))
    			.end()
    			.build();
    }
}
