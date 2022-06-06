package com.billcom.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cagnotte {

	private String custId	;
	private String osmReference;
	private float amount;
	private float cagnotteHT;
	private float cagnotteHtFacilite;
	private Long commitmentPeriod;

	public Cagnotte(String osmReference, float amount, float cagnotteHT, float cagnotteHtFacilite,
			Long commitmentPeriod,String custId ) {
		super();
		this.osmReference = osmReference;
		this.amount = amount;
		this.cagnotteHT = cagnotteHT;
		this.cagnotteHtFacilite = cagnotteHtFacilite;
		this.commitmentPeriod = commitmentPeriod;
		this.custId = custId;
	}

	public Cagnotte() {
		super();
	}

	
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOsmReference() {
		return osmReference;
	}

	public void setOsmReference(String osmReference) {
		this.osmReference = osmReference;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getCagnotteHT() {
		return cagnotteHT;
	}

	public void setCagnotteHT(float cagnotteHT) {
		this.cagnotteHT = cagnotteHT;
	}

	public float getCagnotteHtFacilite() {
		return cagnotteHtFacilite;
	}

	public void setCagnotteHtFacilite(float cagnotteHtFacilite) {
		this.cagnotteHtFacilite = cagnotteHtFacilite;
	}

	public Long getCommitmentPeriod() {
		return commitmentPeriod;
	}

	public void setCommitmentPeriod(Long commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}

}
