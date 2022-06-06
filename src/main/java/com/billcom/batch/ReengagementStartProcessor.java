package com.billcom.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.billcom.service.ReengagementService;
import com.billcom.model.BatchBean;
import com.billcom.model.ReengagementRequest;
import com.billcom.model.RestResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource({ "classpath:batchConfig.properties" })
public class ReengagementStartProcessor implements ItemProcessor<ReengagementRequest,  BatchBean> {
	private Logger log = LoggerFactory.getLogger(ReengagementStartProcessor.class);

	@Autowired
	private ReengagementService reengagementService;

	@Value("${batch.mode}")
	String batchMode;

	
	@Override
	public BatchBean  process(ReengagementRequest reengagementRequest) throws Exception {
		log.info("=> ReengagementStartProcessor IN "+reengagementRequest.getJBPM_Ref_Rengagement() );
		BatchBean batchBean = new BatchBean();
		RestResponse restResponse  = new RestResponse();
		try {
			batchBean = reengagementService.batchModeTraitement(reengagementRequest);
			batchBean.setReengagementRequest(reengagementRequest); 
		}catch(Exception e) {
			e.printStackTrace();
			String errorMessage = "Error message  : " + e.getMessage();
			log.info("Catch Error in ReengagementStartProcessor :: " + errorMessage);
			restResponse.setComment(errorMessage);
			restResponse.setIssuccessful(false);
			batchBean.setRestResponse(restResponse);
			batchBean.setReengagementRequest(reengagementRequest);
		}
		return batchBean;
	}


}
