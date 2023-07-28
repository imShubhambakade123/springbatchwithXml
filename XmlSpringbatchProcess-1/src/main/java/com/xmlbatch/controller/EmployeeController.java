package com.xmlbatch.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class EmployeeController {

	
	@Value("${spring.download.folder.path}")
    String downloadPath;
    
    @Autowired
    JobLauncher jobLauncher;
    
    @Autowired
    @Qualifier("employeeJob")
    Job employeeJob;
    
    @PostMapping("/employee-batch")
    public JobParameters employeeJobProcessing(@RequestParam("file") MultipartFile req) throws IOException {
     	
     	File createfile = new File(downloadPath + req.getOriginalFilename());
         req.transferTo(createfile);

         JobExecution jobExecution = null;

         JobParameters jobParameters = new JobParametersBuilder()
                 .addString("fileName", downloadPath + req.getOriginalFilename())
                 .addLong("startAt", System.currentTimeMillis()).toJobParameters();
         try {
             jobExecution = jobLauncher.run(employeeJob, jobParameters);
             BatchStatus batchstatus = jobExecution.getStatus();
             System.out.println(batchstatus);
         } catch (Exception e) {
             log.error("Exception while dealing with batch processing");
         }
         return jobParameters;

     }
}
