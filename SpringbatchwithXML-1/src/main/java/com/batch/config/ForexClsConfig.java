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

import com.batch.model.ForexCls;
import com.batch.processor.ForexClsProcessor;
import com.batch.repository.ForexClsRepo;

@Configuration
@EnableBatchProcessing
public class ForexClsConfig {

	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     ForexClsRepo forexClsRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<ForexCls> forexClsXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<ForexCls> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(forexClsXmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller forexClsXmlItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(ForexCls.class);
		return unmarshaller;
	}
    
    @Bean
    public ForexClsProcessor forexClsItemprocessor() {
    	return new ForexClsProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<ForexCls> forexClsItemWriter(){
    	RepositoryItemWriter<ForexCls> writer = new RepositoryItemWriter<>();
    	writer.setRepository(forexClsRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step forexClsXmlStep(StaxEventItemReader<ForexCls> forexClsXmlItemReader) {
    	return stepBuilderFactory.get("step-forexCls-xml")
    			.<ForexCls,ForexCls>chunk(10)
    			.reader(forexClsXmlItemReader)
    			.processor(forexClsItemprocessor())
    			.writer(forexClsItemWriter())
    			.build();
    }
    
    @Bean
    public Job forexClsXmlJob(StaxEventItemReader<ForexCls> itemReader) {
    	return jobBuilderFactory.get("job-forexCls-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(forexClsXmlStep(itemReader))
    			.end()
    			.build();
    }
}
