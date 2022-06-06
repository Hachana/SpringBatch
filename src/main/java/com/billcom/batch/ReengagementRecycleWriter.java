package com.billcom.batch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.billcom.model.BatchBean;
import com.billcom.service.ReengagementService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource({ "classpath:batchConfig.properties" })
public class ReengagementRecycleWriter implements ItemWriter<BatchBean> {
	private Logger log = LoggerFactory.getLogger(ReengagementRecycleWriter.class);

	@Autowired
	private ReengagementService reengagementService;

	@Value("${request.status.recycle}")
	Integer statusRecycle;

	@Value("${request.status.completed}")
	Integer statusCompleted;

	@Value("${request.status.failure}")
	int statusEchec;
	
	// Number of restarting the process in case of failure
	@Value("${retryNumber}")
	int retryNumber;

	String jbpmRef = null;
	long orderId = 0;
	String date = null;
	Integer status = null;
	String error = null;

	@Override
	public void write(List<? extends BatchBean> items) throws Exception {
	
		log.info("=> ReengagementRecycleWriter IN ");
		 
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			// get from select reader list of request with status 0 to recycle
			items.stream().forEach(itemss -> {
				if (itemss.getReengagementRequest() != null) {
					if (itemss.getRestResponse().isIssuccessful()) {
						jbpmRef = itemss.getReengagementRequest().getJBPM_Ref_Rengagement();
						log.info("Update request isIssuccessful :: true with JBPM_Ref = " + jbpmRef);

						orderId = itemss.getRestResponse().getId();
						Date entryDate = new Date();
						date = formatter.format(entryDate);
						status = statusCompleted;
						error = "";
						log.info("updateReengagementRequest true -> jbpmRef ::" + jbpmRef + " , orderId ::  " + orderId + " , date :: "
								+ date + " , status :: " + status + " , error ::" + error);
						reengagementService.updateReengagementRequest(jbpmRef, orderId, status, error, date);

					} else {
						log.info("Update request isIssuccessful :: false with JBPM_Ref = "
								+ itemss.getReengagementRequest().getJBPM_Ref_Rengagement());
						// get retry number from REENGAGEMENT_REQUEST
						int count = reengagementService
								.getRetryNumber(itemss.getReengagementRequest().getJBPM_Ref_Rengagement());
						log.info("getRetryNumber from REENGAGEMENT_REQUEST :: " + count);
						// increment the retry number
						count++;
						// the recycle it all depends on the number of retry
						if (count < retryNumber) {
							// update Status To 0 and restart le process
							log.info("RetryNumber from config file :: " + retryNumber + " count =  " + count);
							Date entryDate = new Date();
							date = formatter.format(entryDate);
							jbpmRef = itemss.getReengagementRequest().getJBPM_Ref_Rengagement();
							status = statusRecycle;
							error = itemss.getReengagementRequest().getERROR_MESSAGE();
							orderId = itemss.getRestResponse().getId();
							reengagementService.updateRetryNumber(jbpmRef, count);
							log.info("updateReengagementRequest count < retryNumber -> jbpmRef ::" + jbpmRef + " , orderId ::  " + orderId + " , date :: "
									+ date + " , status :: " + status + " , error ::" + error);
							reengagementService.updateReengagementRequest(jbpmRef, orderId, status, error, date);
						} else {
							log.info("Update request with echec status " + count);
							jbpmRef = itemss.getReengagementRequest().getJBPM_Ref_Rengagement();
							Date entryDate = new Date();
							date = formatter.format(entryDate);
							status = statusEchec;
							error = itemss.getReengagementRequest().getERROR_MESSAGE();
							
							orderId = itemss.getRestResponse().getId();
							log.info("updateReengagementRequest echec -> jbpmRef ::" + jbpmRef + " , orderId ::  " + orderId + " , date :: "
									+ date + " , status :: " + status + " , error ::" + error);
							reengagementService.updateReengagementRequest(jbpmRef, orderId, status, error, date);
							reengagementService.updateRetryNumber(jbpmRef, count);
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
