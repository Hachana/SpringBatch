package com.billcom.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MigrationBean {

	private String offreInit;
	private String offreTarget;

	private List<OptionsToAdd> optionsToAdd ;
	private List<OptionsToDelete> optionsTodelete ;
	private List<ContractList> contractList  ;

	public String getOffreInit() {
		return offreInit;
	}

	public void setOffreInit(String offreInit) {
		this.offreInit = offreInit;
	}

	public String getOffreTarget() {
		return offreTarget;
	}

	public void setOffreTarget(String offreTarget) {
		this.offreTarget = offreTarget;
	}

	public MigrationBean() {
		super();
	}

	public List<ContractList> getContractList() {
		return contractList;
	}

	public void setContractList(List<ContractList> contractList) {
		this.contractList = contractList;
	}

	public List<OptionsToDelete> getOptionsTodelete() {
		return optionsTodelete;
	}

	public void setOptionsTodelete(List<OptionsToDelete> optionsTodelete) {
		this.optionsTodelete = optionsTodelete;
	}

	public List<OptionsToAdd> getOptionsToAdd() {
		return optionsToAdd;
	}

	public void setOptionsToAdd(List<OptionsToAdd> optionsToAdd) {
		this.optionsToAdd = optionsToAdd;
	}

	public MigrationBean(String offreInit, String offreTarget, List<OptionsToAdd> optionsToAdd,
			List<OptionsToDelete> optionsTodelete, List<ContractList> contractList) {
		super();
		this.offreInit = offreInit;
		this.offreTarget = offreTarget;
		this.optionsToAdd = optionsToAdd;
		this.optionsTodelete = optionsTodelete;
		this.contractList = contractList;
	}

}