package com.billcom.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.billcom.model.ReengagementRequest;
import com.billcom.repository.IReengagementRepository;
import org.slf4j.*;

@Component
public class ReengagementRowMapper implements RowMapper<ReengagementRequest> {
	private Logger log = LoggerFactory.getLogger(ReengagementRowMapper.class);

	@Override
	public ReengagementRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
		String ff = rs.getString("JBPM_Ref_Rengagement");
		//System.out.println(" reader*****ffffff*******  " +  ff);
		//reengagementRepository.updateStatus(2, ff);
		ReengagementRequest reengagementRequest = ReengagementRequest.builder()
				.ID(rs.getInt("ID"))
				.ORDER_ID(rs.getInt("ORDER_ID"))
				.JBPM_Ref_Rengagement(rs.getString("JBPM_Ref_Rengagement"))
				.CUST_ID(rs.getString("CUST_ID"))
				.CANAL(rs.getString("CANAL"))
				.DUEDATE(rs.getString("DUEDATE"))
				.OSMREFERENCE(rs.getString("OSMREFERENCE"))
				.MIGARATION(rs.getString("MIGARATION"))
				.UPDATEOPTION(rs.getString("UPDATEOPTION"))
				.UPDATEGRH(rs.getString("UPDATEGRH"))
				.INJECTIONRBF(rs.getString("INJECTIONRBF"))
				.INJECTIONGPS(rs.getString("INJECTIONGPS"))
				.STATUS(rs.getString("STATUS"))
		        .build();
		
	//	System.out.println(" reader*****ccccc*******  " +  reengagementRequest.getJBPM_Ref_Rengagement());
	
		// System.out.println(" reader*****ccccc*******  " +  reengagementRequest.getCUST_ID());
		return reengagementRequest;
	}
}