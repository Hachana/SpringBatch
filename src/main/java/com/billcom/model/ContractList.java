package com.billcom.model;

public class ContractList {

	String coCode;
	String msisdn;
	public String getCocode() {
		return coCode;
	}
	public void setCocode(String cocode) {
		this.coCode = cocode;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	
	@Override
	public String toString() {
		return "contractListBean [cocode=" + coCode + ", msisdn=" + msisdn + "]";
	}
	
	
}
