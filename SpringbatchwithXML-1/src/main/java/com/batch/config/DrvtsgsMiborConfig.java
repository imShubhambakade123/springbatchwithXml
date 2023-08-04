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

import com.batch.model.DrvtsgsMibor;
import com.batch.processor.DrvtsgsMiborProcessor;
import com.batch.repository.DrvtsgsMiborRepo;

@Configuration
@EnableBatchProcessing
public class DrvtsgsMiborConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     DrvtsgsMiborRepo drvtsgsMiborRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<DrvtsgsMibor> drvtsgsMiborXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<DrvtsgsMibor> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(drvtsgsMiborItemMarshaller());
		return reader;
	}

   
	public Unmarshaller drvtsgsMiborItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(DrvtsgsMibor.class);
		return unmarshaller;
	}
    
    @Bean
    public DrvtsgsMiborProcessor drvtsgsMiborItemProcessor() {
    	return new DrvtsgsMiborProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<DrvtsgsMibor> drvtsgsMiborItemWriter(){
    	RepositoryItemWriter<DrvtsgsMibor> writer = new RepositoryItemWriter<>();
    	writer.setRepository(drvtsgsMiborRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step drvtsgsMiborXmlStep(StaxEventItemReader<DrvtsgsMibor> drvtsgsMiborXmlItemReader) {
    	return stepBuilderFactory.get("step-drvtsgs-mibor-xml")
    			.<DrvtsgsMibor,DrvtsgsMibor>chunk(10)
    			.reader(drvtsgsMiborXmlItemReader)
    			.processor(drvtsgsMiborItemProcessor())
    			.writer(drvtsgsMiborItemWriter())
    			.build();
    }
    
    @Bean
    public Job drvtsgsMiborXmlJob(StaxEventItemReader<DrvtsgsMibor> xmlitemReader) {
    	return jobBuilderFactory.get("job-drvtsgs-mibor-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(drvtsgsMiborXmlStep(xmlitemReader))
    			.end()
    			.build();
    }
}

