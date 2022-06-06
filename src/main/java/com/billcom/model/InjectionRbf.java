package com.billcom.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InjectionRbf {

	private String assignMode;
	private String canalName;
	private String csId;
	private String packId;
	private String period;
	private String userName;
	private String validDate;

	public String getAssignMode() {
		return assignMode;
	}

	public void setAssignMode(String assignMode) {
		this.assignMode = assignMode;
	}

	public String getCanalName() {
		return canalName;
	}

	public void setCanalName(String canalName) {
		this.canalName = canalName;
	}

	public String getCsId() {
		return csId;
	}

	public void setCsId(String csId) {
		this.csId = csId;
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public InjectionRbf(String assignMode, String canalName, String csId, String packId, String period, String userName,
			String validDate) {
		super();
		this.assignMode = assignMode;
		this.canalName = canalName;
		this.csId = csId;
		this.packId = packId;
		this.period = period;
		this.userName = userName;
		this.validDate = validDate;
	}

	public InjectionRbf() {
		super();
	}

}