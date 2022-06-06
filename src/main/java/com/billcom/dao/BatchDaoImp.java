package com.billcom.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.billcom.repository.IReengagementRepository;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
@Repository
@Slf4j
@PropertySource({ "classpath:batchConfig.properties" })
public class BatchDaoImp implements BatchDao {
	private Logger log = LoggerFactory.getLogger(BatchDaoImp.class);

	@Value("${request.status.initial}")
	int statusInitial;

	@Value("${request.status.recycle}")
	int statusRecycle;

	@Value("${request.status.failure}")
	int statusEchec;

	@Value("${request.status.inProgress}")
	int inProgress;
	



	@Autowired
	private IReengagementRepository reengagementRepository;

	@Override
	public void updateStatus(String jbpmRef, Long orderId, Integer status, String errorMessage, String entryDate) {
		log.info("=>updateStatus Dao IN " + errorMessage);
		
		reengagementRepository.updateRequestStatus(status, jbpmRef, orderId, errorMessage, entryDate);
		log.info("<=updateStatus Dao out");
	}

	@Override
	public int getRetryNumber(String jbpmRef) {
		log.info("=>getRetryNumber Dao IN " + jbpmRef);
		int retryNumber = reengagementRepository.getRetryNumber(jbpmRef);
		log.info("=>getRetryNumber Dao OUT " + jbpmRef);
		return retryNumber;
	}

	@Override
	public void updateRetryNumber(String jbpmRef, int retryNumber) {
		log.info("=>updateRetryNumber Dao IN " + jbpmRef + " retryNumber " + retryNumber);
		reengagementRepository.updateRetryNumber(jbpmRef, retryNumber);

	}

	@Override
	public Long getIdMin(int status) {
		Long min = null;
		log.info("=>getIdMin "+reengagementRepository );
		if (reengagementRepository.getIdMin(status) != null) {
			min = reengagementRepository.getIdMin(status);
		}
		return min;
	}

	@Override
	public Long getIdMax(int status) {
		Long max = null;
		if (reengagementRepository.getIdMax(status) != null) {
			max = reengagementRepository.getIdMax(status);
		}
		return max;
	}

}
