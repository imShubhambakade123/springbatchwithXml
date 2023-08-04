//package com.batch.config;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.xml.StaxEventItemReader;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.oxm.Unmarshaller;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//
//import com.batch.model.Securities;
//import com.batch.processor.SecuritiesProcessor;
//import com.batch.repository.SecuritiesRepo;
//
//@Configuration
//@EnableBatchProcessing
//public class SecuritiesConfig {
//
//	@Autowired
//	JobBuilderFactory jobBuilderFactory;
//	
//	@Autowired
//	StepBuilderFactory stepBuilderFactory;
//	
//	 @Autowired
//     SecuritiesRepo securitiesRepo;
//	
//	@Bean
//	@StepScope
//	public StaxEventItemReader<Securities> securitiesXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
//		StaxEventItemReader<Securities> reader = new StaxEventItemReader<>();
//		reader.setResource(new FileSystemResource(fileName));
//		reader.setFragmentRootElementName("ROW");
//		reader.setUnmarshaller(securitiesXmlItemMarshaller());
//		return reader;
//	}
//
//    @Bean
//	public Unmarshaller securitiesXmlItemMarshaller() {
//		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
//		unmarshaller.setClassesToBeBound(Securities.class);
//		return unmarshaller;
//	}
//    
//    @Bean
//    public SecuritiesProcessor securitiesItemProcessor() {
//    	return new SecuritiesProcessor();
//    }
//    
//    @Bean
//    public RepositoryItemWriter<Securities> securitiesItemWriter(){
//    	RepositoryItemWriter<Securities> writer = new RepositoryItemWriter<>();
//    	writer.setRepository(securitiesRepo);
//    	writer.setMethodName("save");
//    	return writer;
//    }
//    
//    @Bean
//    public Step securitiesXmlStep(StaxEventItemReader<Securities> securitiesXmlItemReader) {
//    	return stepBuilderFactory.get("step-securities-xml")
//    			.<Securities,Securities>chunk(10)
//    			.reader(securitiesXmlItemReader)
//    			.processor(securitiesItemProcessor())
//    			.writer(securitiesItemWriter())
//    			.build();
//    }
//    
//    @Bean
//    public Job securitiesXmlJob(StaxEventItemReader<Securities> itemReader) {
//    	return jobBuilderFactory.get("job-securities-xml")
//    			.incrementer(new RunIdIncrementer())
//    			.flow(securitiesXmlStep(itemReader))
//    			.end()
//    			.build();
//    }
//}
