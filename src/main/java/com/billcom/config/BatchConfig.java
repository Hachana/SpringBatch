package com.billcom.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;

import com.billcom.batch.ReengagementRecycleProcessor;
import com.billcom.batch.ReengagementRecycleWriter;
import com.billcom.batch.ReengagementStartProcessor;
import com.billcom.batch.ReengagementStartWriter;
import com.billcom.mapper.ReengagementRowMapper;
import com.billcom.model.BatchBean;
import com.billcom.model.ReengagementRequest;
import com.billcom.partitioner.RangePartitionerForRecycle;
import com.billcom.partitioner.RangePartitionerForStart;
import com.billcom.repository.IReengagementRepository;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;

@Slf4j
@PropertySource({ "classpath:batchConfig.properties" })
@PropertySource({ "classpath:batchProcessingSql.properties" })
@Configuration
@EnableBatchProcessing
public class BatchConfig {
	private Logger log = LoggerFactory.getLogger(BatchConfig.class);

	//private static final String JOB_NAME = "reengagementJob";
	private static final String STEP_NAM_START = "startStep";
	private static final String STEP_NAME_RECYCLE = "recycleStep";

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Value("${chunk.oriented.processing}")
	int chunk;

	@Value("${batch.mode}")
	String batchMode;
	
	@Value("${batch.thread.poolSize}")
	int poolSize;
	
	@Value("${batch.thread.maxPoolSize}")
	int maxPoolSize;
	
	@Value("${batch.thread.queueCapacity}")
	int queueCapacity;
	
	@Value("${batch.thread.gridSize}")
	int gridSize;

	@Value("${Q001}")
	String Q001;
	

	@Value("${request.status.initial}")
	int statusInitial;

	@Value("${request.status.recycle}")
	int statusRecycle;
	
	@Value("${request.status.inProgress}")
	int inProgress;

	  /* @Override
	    public void setDataSource(DataSource dataSource) {
	        super.setDataSource(null);
	    }*/
	// call partitioning traitment for Start
	@Bean
	public RangePartitionerForStart partitionerStart() {
		RangePartitionerForStart columnRangePartitioner = new RangePartitionerForStart();
		return columnRangePartitioner;
	}
	
	@Bean
	public RangePartitionerForRecycle partitionerRecycle() {
		RangePartitionerForRecycle columnRangePartitioner = new RangePartitionerForRecycle();
		return columnRangePartitioner;
	}

	// slave step For Start
	@Bean
	public Step slaveStepForStart() {
		return stepBuilderFactory.get("slaveStepForStart")
				.<ReengagementRequest, BatchBean>chunk(chunk)
				.reader(pagingItemReaderForStart(null, null))
				.processor(processorForStart())
				.writer(reengagementRequestItemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}

	// Master For Start
	@Bean
	public Step startStep() {
		return stepBuilderFactory.get(STEP_NAM_START)
				.partitioner(slaveStepForStart().getName(), partitionerStart())
				.step(slaveStepForStart())
				.gridSize(gridSize)
				.build();
	}

	// slave step For Recycle
	@Bean
	public Step slaveStepForRecycle() {
		return stepBuilderFactory.get("slaveStep")
				.<ReengagementRequest, BatchBean>chunk(chunk)
				.reader(pagingItemReaderForRecycle(null, null))
				.processor(processorForRecycle())
				.writer(reengagementRequestItemWriterForRecycle())
				.taskExecutor(taskExecutor())
				.build();
	}

	// Master For Recycle
	@Bean
	public Step recycleStep() {
		return stepBuilderFactory.get(STEP_NAME_RECYCLE)
				.partitioner(slaveStepForRecycle().getName(), partitionerRecycle())
				.step(slaveStepForRecycle())
				.gridSize(gridSize)
				.build();
	}

	@Bean
	public Job processJobStart() throws Exception {
			log.info("In job for start ");
			return jobBuilderFactory.get("processJobStart")
					.incrementer(new RunIdIncrementer())
					.start(startStep())
					.build();
	}
		
	@Bean
	public Job processJobRecycle() throws Exception {
			log.info("In job for recycle ");
			return jobBuilderFactory.get("processJobRecycle")
					.incrementer(new RunIdIncrementer())
					.start(recycleStep())
					.build();
	}
	
	@Bean
	public Job processJobParallel() throws Exception {
			log.info("In job for parallel ");
			Flow flow1 = new FlowBuilder<SimpleFlow>("FlowForStart")
					.start(startStep())
					.build();
			Flow flow2 = new FlowBuilder<SimpleFlow>("FlowForRecycle")
					.start(recycleStep())
					.build();

			return jobBuilderFactory.get("processJobParallel").start(flow1)
					.split(new SimpleAsyncTaskExecutor())
					.add(flow2)
					.end()
					.build();
	}

	@Bean
	public ReengagementStartProcessor processorForStart() {
		log.info("In processorForStart for reengagement ");
		return new ReengagementStartProcessor();
	}

	@Bean
	public ItemWriter<BatchBean> reengagementRequestItemWriter() {
		log.info("In reengagementRequestItemWriter ForStart ");
		return new ReengagementStartWriter();
	}

	@Bean
	public ReengagementRecycleProcessor processorForRecycle() {
		log.info("In processorForRecycle ");
		return new ReengagementRecycleProcessor();
	}

	@Bean
	public ItemWriter<BatchBean> reengagementRequestItemWriterForRecycle() {
		log.info("In reengagementRequestItemWriterForRecycle ");
		return new ReengagementRecycleWriter();
	}

	//Get request from REENGAGEMENT_REQUEST with status inial with JdbcPagingItemReader to be synchrone
	@Bean
	@StepScope
	public JdbcPagingItemReader<ReengagementRequest> pagingItemReaderForStart(
			//get minValue & maxValue from partitionerStart with type :: stepExecutionContext
			@Value("#{stepExecutionContext['minValue']}") Long minValue,
			@Value("#{stepExecutionContext['maxValue']}") Long maxValue) {
		log.info("reading " + minValue + " to " + maxValue);
		
		final JdbcPagingItemReader<ReengagementRequest> reader = new  JdbcPagingItemReader<>();
		   reader.setDataSource(dataSource);
		   reader.setFetchSize(100);
		   reader.setPageSize(100);
		   reader.setRowMapper(new ReengagementRowMapper());
		   reader.setQueryProvider(createQuery());
		   Map<String, Object> parameters = new HashMap<>();
		   parameters.put("minValue", minValue);
		   parameters.put("maxValue", maxValue);
		   parameters.put("status", inProgress);
		   reader.setParameterValues(parameters);
		return reader;
	}
	
	@Bean
	@StepScope
	public JdbcPagingItemReader<ReengagementRequest> pagingItemReaderForRecycle(
			@Value("#{stepExecutionContext['minValue']}") Long minValue,
			@Value("#{stepExecutionContext['maxValue']}") Long maxValue) {
		
		log.info("reading " + minValue + " to " + maxValue);

		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("ID", Order.ASCENDING);
		
		final JdbcPagingItemReader<ReengagementRequest> reader = new JdbcPagingItemReader<>();
		   reader.setDataSource(dataSource);
		   reader.setFetchSize(100);
		   reader.setPageSize(100);
		   reader.setRowMapper(new ReengagementRowMapper());
		   reader.setQueryProvider(createQuery());
		   Map<String, Object> parameters = new HashMap<>();
		   parameters.put("minValue", minValue);
		   parameters.put("maxValue", maxValue);
		   parameters.put("status", statusRecycle);
		   reader.setParameterValues(parameters);
		
		return reader;
	}
	
	 private MySqlPagingQueryProvider createQuery() {
		   final Map<String, Order> sortKeys = new HashMap<>();
		   sortKeys.put("ID", Order.ASCENDING);
		   final MySqlPagingQueryProvider queryProvider = new  MySqlPagingQueryProvider();
		   queryProvider.setSelectClause("*");
		   queryProvider.setFromClause(getFromClause());
		   queryProvider.setSortKeys(sortKeys);
		   return queryProvider;
		 }
		 private String getFromClause() {
			 log.info("-> Query to read :: "+Q001);
		   return "( " + Q001 + ")" + " AS RESULT_TABLE ";
		 }
	

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(poolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("Thread-");
		return executor;
	}

}