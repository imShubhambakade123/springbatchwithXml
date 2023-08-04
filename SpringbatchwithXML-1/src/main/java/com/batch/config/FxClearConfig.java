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

import com.batch.model.FxClear;
import com.batch.processor.FxClearProcessor;
import com.batch.repository.FxClearRepo;

@Configuration
@EnableBatchProcessing
public class FxClearConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     FxClearRepo fxClearRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<FxClear> fxClearXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<FxClear> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(fxClearXmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller fxClearXmlItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(FxClear.class);
		return unmarshaller;
	}
    
    @Bean
    public FxClearProcessor fxClearItemProcessor() {
    	return new FxClearProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<FxClear> fxClearItemWriter(){
    	RepositoryItemWriter<FxClear> writer = new RepositoryItemWriter<>();
    	writer.setRepository(fxClearRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step fxClearXmlStep(StaxEventItemReader<FxClear> fxClearXmlItemReader) {
    	return stepBuilderFactory.get("step-fxClear-xml")
    			.<FxClear,FxClear>chunk(10)
    			.reader(fxClearXmlItemReader)
    			.processor(fxClearItemProcessor())
    			.writer(fxClearItemWriter())
    			.build();
    }
    
    @Bean
    public Job fxClearXmlJob(StaxEventItemReader<FxClear> itemReader) {
    	return jobBuilderFactory.get("job-fxClear-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(fxClearXmlStep(itemReader))
    			.end()
    			.build();
    }
}
