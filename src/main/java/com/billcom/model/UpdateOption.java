package com.billcom.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateOption {

	private String offreTarget;
	private List<OptionsToAdd> optionsToAdd = new ArrayList<>();
	private List<OptionsToDelete> optionsToDelete = new ArrayList<>();

	private List<ContractList> contractList = new ArrayList<>();

	public UpdateOption(String offreTarget, List<OptionsToAdd> optionsToAdd,
			List<OptionsToDelete> optionsToRemove, List<ContractList> contractList) {
		super();
		this.offreTarget = offreTarget;
		this.optionsToAdd = optionsToAdd;
		this.optionsToDelete = optionsToRemove;
		this.contractList = contractList;
	}

	public UpdateOption() {
		super();
	}

	public String getOffreTarget() {
		return offreTarget;
	}

	public void setOffreTarget(String offreTarget) {
		this.offreTarget = offreTarget;
	}

	public List<OptionsToAdd> getOptionsToAdd() {
		return optionsToAdd;
	}

	public void setOptionsToAdd(List<OptionsToAdd> optionsToAdd) {
		this.optionsToAdd = optionsToAdd;
	}


	public List<OptionsToDelete> getOptionsToDelete() {
		return optionsToDelete;
	}

	public void setOptionsToDelete(List<OptionsToDelete> optionsToDelete) {
		this.optionsToDelete = optionsToDelete;
	}

	public List<ContractList> getContractList() {
		return contractList;
	}

	public void setContractList(List<ContractList> contractList) {
		this.contractList = contractList;
	}

}
