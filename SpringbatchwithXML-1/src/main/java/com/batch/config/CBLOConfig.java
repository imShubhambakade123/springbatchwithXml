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

import com.batch.model.CBLO;
import com.batch.processor.CBLOProcessor;
import com.batch.repository.CBLORepo;

@Configuration
@EnableBatchProcessing
public class CBLOConfig {

	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
    CBLORepo cbloRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<CBLO> cbloXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<CBLO> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(xmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller xmlItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(CBLO.class);
		return unmarshaller;
	}
    
    @Bean
    public CBLOProcessor cbloItemprocessor() {
    	return new CBLOProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<CBLO> cbloItemWriter(){
    	RepositoryItemWriter<CBLO> writer = new RepositoryItemWriter<>();
    	writer.setRepository(cbloRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step cbloXmlStep(ItemReader<CBLO> cbloXmlItemReader) {
    	return stepBuilderFactory.get("step-cblo-xml")
    			.<CBLO,CBLO>chunk(10)
    			.reader(cbloXmlItemReader)
    			.processor(cbloItemprocessor())
    			.writer(cbloItemWriter())
    			.build();
    }
    
    @Bean
    public Job cbloXmlJob(ItemReader<CBLO> itemReader) {
    	return jobBuilderFactory.get("job-cblo-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(cbloXmlStep(itemReader))
    			.end()
    			.build();
    }
}
