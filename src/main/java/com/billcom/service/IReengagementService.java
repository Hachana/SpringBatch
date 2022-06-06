package com.billcom.service;

import com.billcom.model.BatchBean;
import com.billcom.model.ReengagementRequest;

public interface IReengagementService {
	
	 void updateReengagementRequest(String jbpmRef, Long orderId, Integer status, String errorMessage,  String entryDate);
	 
	 public BatchBean batchModeTraitement (ReengagementRequest reengagementRequestList) throws java.lang.Exception ;
	 
	 int getRetryNumber(String jbpmRef);
		
     void updateRetryNumber(String jbpmRef,int retryNumber);

}
