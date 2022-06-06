package com.billcom.model;

public class OperationResponse implements java.io.Serializable {

    
    private static final long serialVersionUID = 1L;

    public OperationResponse() {
    }
    
    /**
     * Result of the execution
     */
    private Boolean isSuccessful;

    /*
     * Error Code
     */
    private String bscsErrorCode;

    /**
     * Execution details
     */
    private String comment;

    public Boolean getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getBscsErrorCode() {
        return bscsErrorCode;
    }

    public void setBscsErrorCode(String bscsErrorCode) {
        this.bscsErrorCode = bscsErrorCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

