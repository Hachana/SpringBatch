package com.billcom;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@PropertySource({ "classpath:batchConfig.properties" })
@Component
@EnableScheduling
public class BatchLauncher {

	@Autowired
	JobLauncher jobLauncher;

	/*
	 * @Autowired Job job;
	 */

	@Autowired
	@Qualifier("processJobStart")
	private Job processJobStart;

	@Autowired
	@Qualifier("processJobRecycle")
	private Job processJobRecycle;

	@Autowired
	@Qualifier("processJobParallel")
	private Job processJobParallel;

	@Value("${batch.mode}")
	String batchMode;

	public BatchStatus run() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, java.lang.Exception {
		Job job = null;
		if (batchMode.equalsIgnoreCase("Start")) {
			job = processJobStart;
		} else if (batchMode.equalsIgnoreCase("Recycle")) {
			job = processJobRecycle;
		} else {
			job = processJobParallel;
		}
		JobParameters parameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.toJobParameters();
		JobExecution jobExecution = jobLauncher.run(job, parameters);
		return jobExecution.getStatus();
	}
}