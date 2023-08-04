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
//import com.batch.model.Secothr;
//import com.batch.processor.SecothrProcessor;
//import com.batch.repository.SecothrRepo;
//
//@Configuration
//@EnableBatchProcessing
//public class SecothrConfig {
//
//	@Autowired
//	JobBuilderFactory jobBuilderFactory;
//	
//	@Autowired
//	StepBuilderFactory stepBuilderFactory;
//	
//	 @Autowired
//     SecothrRepo secothrRepo;
//	
//	@Bean
//	@StepScope
//	public StaxEventItemReader<Secothr> secothrXmlItemReader(@Value("#{jobParameters[fileName]}") String fileName){
//		StaxEventItemReader<Secothr> reader = new StaxEventItemReader<>();
//		reader.setResource(new FileSystemResource(fileName));
//		reader.setFragmentRootElementName("ROW");
//		reader.setUnmarshaller(secothrXmlItemMarshaller());
//		return reader;
//	}
//
//    @Bean
//	public Unmarshaller secothrXmlItemMarshaller() {
//		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
//		unmarshaller.setClassesToBeBound(Secothr.class);
//		return unmarshaller;
//	}
//    
//    @Bean
//    public SecothrProcessor secothrItemprocessor() {
//    	return new SecothrProcessor();
//    }
//    
//    @Bean
//    public RepositoryItemWriter<Secothr> secothrItemWriter(){
//    	RepositoryItemWriter<Secothr> writer = new RepositoryItemWriter<>();
//    	writer.setRepository(secothrRepo);
//    	writer.setMethodName("save");
//    	return writer;
//    }
//    
//    @Bean
//    public Step secothrXmlStep(StaxEventItemReader<Secothr> secothrXmlItemReader) {
//    	return stepBuilderFactory.get("step-secothr-xml")
//    			.<Secothr,Secothr>chunk(10)
//    			.reader(secothrXmlItemReader)
//    			.processor(secothrItemprocessor())
//    			.writer(secothrItemWriter())
//    			.build();
//    }
//    
//    @Bean
//    public Job secothrXmlJob(StaxEventItemReader<Secothr> itemReader) {
//    	return jobBuilderFactory.get("job-secothr-xml")
//    			.incrementer(new RunIdIncrementer())
//    			.flow(secothrXmlStep(itemReader))
//    			.end()
//    			.build();
//    }
//}
