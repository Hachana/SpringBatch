package com.billcom.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JbpmReengagement {
	private String custId;
    private String canal;
    private String duedate;
    private String osmReference;
    
    private List <UpdateOption>  updateOption= new ArrayList<>();
    private List <InjectionRbf>  injectionRbf= new ArrayList<>();
    private UpdateGrh updateGrh;   
    private InjectionGps injectionGps;
    private List <MigrationBean>  migration = new ArrayList<>();
    private String jbpmrefreegagement;

    
	public JbpmReengagement(String custId, String canal, String duedate, String osmReference,
			List<UpdateOption> updateOption, List<InjectionRbf> injectionRbf, UpdateGrh updateGrh,
			InjectionGps injectionGps, List<MigrationBean> migration, String jbpmrefreegagement) {
		super();
		this.custId = custId;
		this.canal = canal;
		this.duedate = duedate;
		this.osmReference = osmReference;
		this.updateOption = updateOption;
		this.injectionRbf = injectionRbf;
		this.updateGrh = updateGrh;
		this.injectionGps = injectionGps;
		this.migration = migration;
		this.jbpmrefreegagement=jbpmrefreegagement;
	}
	
	
	public String getJbpmrefreegagement() {
		return jbpmrefreegagement;
	}


	public void setJbpmrefreegagement(String jbpmrefreegagement) {
		this.jbpmrefreegagement = jbpmrefreegagement;
	}


	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCanal() {
		return canal;
	}
	public void setCanal(String canal) {
		this.canal = canal;
	}
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getOsmReference() {
		return osmReference;
	}
	public void setOsmReference(String osmReference) {
		this.osmReference = osmReference;
	}
	public List<UpdateOption> getUpdateOption() {
		return updateOption;
	}
	public void setUpdateOption(List<UpdateOption> updateOption) {
		this.updateOption = updateOption;
	}
	
	public List<InjectionRbf> getInjectionRbf() {
		return injectionRbf;
	}
	public void setInjectionRbf(List<InjectionRbf> injectionRbf) {
		this.injectionRbf = injectionRbf;
	}
	public UpdateGrh getUpdateGrh() {
		return updateGrh;
	}
	public void setUpdateGrh(UpdateGrh updateGrh) {
		this.updateGrh = updateGrh;
	}
	public InjectionGps getInjectionGps() {
		return injectionGps;
	}
	public void setInjectionGps(InjectionGps injectionGps) {
		this.injectionGps = injectionGps;
	}
	public List<MigrationBean> getMigration() {
		return migration;
	}
	public void setMigration(List<MigrationBean> migration) {
		this.migration = migration;
	}
	public JbpmReengagement() {
		super();
	}


	@Override
	public String toString() {
		return "JbpmReengagement [custId=" + custId + ", canal=" + canal + ", duedate=" + duedate + ", osmReference="
				+ osmReference + ", updateOption=" + updateOption + ", injectionRbf=" + injectionRbf + ", updateGrh="
				+ updateGrh + ", injectionGps=" + injectionGps + ", migration=" + migration + ", jbpmrefreegagement="
				+ jbpmrefreegagement + "]";
	}
    
	
    
}
