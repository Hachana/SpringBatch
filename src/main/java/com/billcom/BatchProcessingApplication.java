package com.billcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
public class BatchProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchProcessingApplication.class, args);
	}

}
