package com.billcom.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Entity
@Table(name = "REENGAGEMENT_REQUEST")
public class ReengagementRequest {
	
	
	 @Id
     @Column(name = "ID")
	 @GeneratedValue(strategy = GenerationType.SEQUENCE)
	 private Integer ID;
	
    @Column(name = "ORDER_ID", nullable = false)
	private Integer ORDER_ID;
	
	@Column(name = "JBPM_Ref_Rengagement")
	private String JBPM_Ref_Rengagement;
	
	@Column(name = "CUST_ID")
	private String CUST_ID;
	
	@Column(name = "CANAL")
	private String CANAL;
	
	@Column(name = "DUEDATE")
	private String DUEDATE;
	
	@Column(name = "OSMREFERENCE")
	private String OSMREFERENCE;
	
	@Column(name = "MIGARATION")
	private String MIGARATION;
	
	@Column(name = "UPDATEOPTION")
	private String UPDATEOPTION;
	
	@Column(name = "UPDATEGRH")
	private String UPDATEGRH;
	
	@Column(name = "INJECTIONRBF")
	private String INJECTIONRBF;
	
	@Column(name = "INJECTIONGPS")
	private String INJECTIONGPS;
	
	@Column(name = "ENTRY_DATE")
	private Date ENTRY_DATE;
	
	@Column(name = "LAST_MODDATE")
	private Date LAST_MODDATE;
	
	@Column(name = "STATUS")
	private String STATUS;
	
	
	@Column(name = "ERROR_MESSAGE")
	private String ERROR_MESSAGE;


	@Override
	public String toString() {
		return "ReengagementRequest [ID=" + ID + ", ORDER_ID=" + ORDER_ID + ", JBPM_Ref_Rengagement="
				+ JBPM_Ref_Rengagement + ", CUST_ID=" + CUST_ID + ", CANAL=" + CANAL + ", DUEDATE=" + DUEDATE
				+ ", OSMREFERENCE=" + OSMREFERENCE + ", MIGARATION=" + MIGARATION + ", UPDATEOPTION=" + UPDATEOPTION
				+ ", UPDATEGRH=" + UPDATEGRH + ", INJECTIONRBF=" + INJECTIONRBF + ", INJECTIONGPS=" + INJECTIONGPS
				+ ", ENTRY_DATE=" + ENTRY_DATE + ", LAST_MODDATE=" + LAST_MODDATE + ", STATUS=" + STATUS
				+ ", ERROR_MESSAGE=" + ERROR_MESSAGE + "]";
	}

	

	
	

}
