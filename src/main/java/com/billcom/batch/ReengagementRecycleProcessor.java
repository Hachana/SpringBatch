package com.billcom.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.billcom.Scheduler;
import com.billcom.model.BatchBean;
import com.billcom.model.ReengagementRequest;
import com.billcom.model.RestResponse;
import com.billcom.service.ReengagementService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReengagementRecycleProcessor implements ItemProcessor<ReengagementRequest, BatchBean> {
	private Logger log = LoggerFactory.getLogger(ReengagementRecycleProcessor.class);
	@Autowired
	private ReengagementService reengagementService;

	@Override
	public BatchBean process(ReengagementRequest reengagementRequest) throws Exception {
		log.info("=> ReengagementRecycleProcessor IN " + reengagementRequest.getJBPM_Ref_Rengagement());
		BatchBean batchBean = new BatchBean();
		RestResponse restResponse  = new RestResponse();
		try {
			batchBean = reengagementService.batchModeTraitement(reengagementRequest);
			batchBean.setReengagementRequest(reengagementRequest);
		} catch (Exception e) {
			String errorMessage = "Error message  : " + e.getMessage();
			log.info("Catch Error in RecycleProcessor :: " + errorMessage);
			restResponse.setComment(errorMessage);
			restResponse.setIssuccessful(false);
			batchBean.setRestResponse(restResponse);
			batchBean.setReengagementRequest(reengagementRequest);
		}
		log.info("<= ReengagementRecycleProcessor OUT");
		return batchBean;
	}

}
