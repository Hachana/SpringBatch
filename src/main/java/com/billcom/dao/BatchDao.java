package com.billcom.dao;


public interface BatchDao {
	
	void updateStatus( String jbpmRef,Long orderId,Integer status ,String errorMessage , String entryDate);
	
	int getRetryNumber(String jbpmRef);
	
	void updateRetryNumber(String jbpmRef,int retryNumber);
	
	Long getIdMin(int status);
	
	Long getIdMax(int status);
	
}
