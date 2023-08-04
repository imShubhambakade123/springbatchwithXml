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

import com.batch.model.FxSwap;
import com.batch.processor.FxSwapProcessor;
import com.batch.repository.FxSwapRepo;

@Configuration
@EnableBatchProcessing
public class FxSwapConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	 @Autowired
     FxSwapRepo fxSwapRepo;
	
	@Bean
	@StepScope
	public StaxEventItemReader<FxSwap> fxSwapXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
		StaxEventItemReader<FxSwap> reader = new StaxEventItemReader<>();
		reader.setResource(new FileSystemResource(fileName));
		reader.setFragmentRootElementName("ROW");
		reader.setUnmarshaller(fxSwapXmlItemMarshaller());
		return reader;
	}

    @Bean
	public Unmarshaller fxSwapXmlItemMarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(FxSwap.class);
		return unmarshaller;
	}
    
    @Bean
    public FxSwapProcessor fxSwapItemProcessor() {
    	return new FxSwapProcessor();
    }
    
    @Bean
    public RepositoryItemWriter<FxSwap> fxSwapItemWriter(){
    	RepositoryItemWriter<FxSwap> writer = new RepositoryItemWriter<>();
    	writer.setRepository(fxSwapRepo);
    	writer.setMethodName("save");
    	return writer;
    }
    
    @Bean
    public Step fxSwapXmlStep(StaxEventItemReader<FxSwap> fxSwapXmlItemReader) {
    	return stepBuilderFactory.get("step-fxswap-xml")
    			.<FxSwap,FxSwap>chunk(10)
    			.reader(fxSwapXmlItemReader)
    			.processor(fxSwapItemProcessor())
    			.writer(fxSwapItemWriter())
    			.build();
    }
    
    @Bean
    public Job fxSwapXmlJob(StaxEventItemReader<FxSwap> itemReader) {
    	return jobBuilderFactory.get("job-fxSwap-xml")
    			.incrementer(new RunIdIncrementer())
    			.flow(fxSwapXmlStep(itemReader))
    			.end()
    			.build();
    }
}
