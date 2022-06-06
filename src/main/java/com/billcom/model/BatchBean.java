package com.billcom.model;

import java.util.List;

public class BatchBean {
	

	private  List<ReengagementRequest> reengagementRequestr;
	private  ReengagementRequest reengagementRequest;
    private boolean isSuccful;
    private Long orderId;
    private String errorMessage;
    private RestResponse restResponse;
	
    
    

	public List<ReengagementRequest> getReengagementRequestr() {
		return reengagementRequestr;
	}


	public void setReengagementRequestr(List<ReengagementRequest> reengagementRequestr) {
		this.reengagementRequestr = reengagementRequestr;
	}


	public RestResponse getRestResponse() {
		return restResponse;
	}
	

	public void setRestResponse(RestResponse restResponse) {
		this.restResponse = restResponse;
	}

	public ReengagementRequest getReengagementRequest() {
		return reengagementRequest;
	}

	public void setReengagementRequest(ReengagementRequest reengagementRequest) {
		this.reengagementRequest = reengagementRequest;
	}

	public boolean isSuccful() {
		return isSuccful;
	}

	public void setSuccful(boolean isSuccful) {
		this.isSuccful = isSuccful;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}




	
	
}
