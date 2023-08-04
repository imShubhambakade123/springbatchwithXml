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

import com.batch.model.ForexTr;
import com.batch.processor.ForexTrProcessor;
import com.batch.repository.ForexTrRepo;

@Configuration
@EnableBatchProcessing
public class ForexTrConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     ForexTrRepo forexTrRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<ForexTr> forexTrXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<ForexTr> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(forexTrXmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller forexTrXmlItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(ForexTr.class);
		return unmarshaller;
	}
    
    @Bean
    public ForexTrProcessor forexTrItemprocessor() {
    	return new ForexTrProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<ForexTr> forexTrItemWriter(){
    	RepositoryItemWriter<ForexTr> writer = new RepositoryItemWriter<>();
    	writer.setRepository(forexTrRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step forexTrXmlStep(StaxEventItemReader<ForexTr> forexTrXmlItemReader) {
    	return stepBuilderFactory.get("step-forexTr-xml")
    			.<ForexTr,ForexTr>chunk(10)
    			.reader(forexTrXmlItemReader)
    			.processor(forexTrItemprocessor())
    			.writer(forexTrItemWriter())
    			.build();
    }
    
    @Bean
    public Job forexTrXmlJob(StaxEventItemReader<ForexTr> itemReader) {
    	return jobBuilderFactory.get("job-forexTr-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(forexTrXmlStep(itemReader))
    			.end()
    			.build();
    }
}
