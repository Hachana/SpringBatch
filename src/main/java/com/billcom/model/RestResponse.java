package com.billcom.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RestResponse {
	


	private long id;
	@JsonProperty("isSuccessful")
    private boolean issuccessful;
    private String comment;
    private String errorCode;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isIssuccessful() {
		return issuccessful;
	}

	public void setIssuccessful(boolean issuccessful) {
		this.issuccessful = issuccessful;
	}



  
}