package com.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SpringbatchwithXmlApplication {

	public static void main(String[] args) {
		
		
	
	ConfigurableApplicationContext context = SpringApplication.run(SpringbatchwithXmlApplication.class, args);
		JobLauncher jobLauncher = context.getBean(JobLauncher.class);
		Job job = context.getBean("employeeXmljob",Job.class);
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("time",System.currentTimeMillis())
				.toJobParameters();
		
		try {
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.close();
        }
	}
	}


