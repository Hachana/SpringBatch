package com.billcom.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OptionsToAdd {
	private String option;

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public OptionsToAdd(String option) {
		super();
		this.option = option;
	}

	public OptionsToAdd() {
		super();
	}

}
