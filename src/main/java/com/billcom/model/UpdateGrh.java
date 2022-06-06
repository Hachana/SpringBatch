package com.billcom.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateGrh {

	private Long commitmentPeriod;
	private String commitmentStartDate;
	private List<ContractList> contractList = new ArrayList<>();

	public Long getCommitmentPeriod() {
		return commitmentPeriod;
	}

	public void setCommitmentPeriod(Long commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}

	public String getCommitmentStartDate() {
		return commitmentStartDate;
	}

	public void setCommitmentStartDate(String commitmentStartDate) {
		this.commitmentStartDate = commitmentStartDate;
	}

	public UpdateGrh() {
		super();
	}

	public UpdateGrh(Long commitmentPeriod, String commitmentStartDate, List<ContractList> contractList) {
		super();
		this.commitmentPeriod = commitmentPeriod;
		this.commitmentStartDate = commitmentStartDate;
		this.contractList = contractList;
	}

	public List<ContractList> getContractList() {
		return contractList;
	}

	public void setContractList(List<ContractList> contractList) {
		this.contractList = contractList;
	}

}
