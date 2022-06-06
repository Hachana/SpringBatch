package com.billcom.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.billcom.dao.BatchDaoImp;
import com.billcom.model.BatchBean;
import com.billcom.model.InjectionGps;
import com.billcom.model.InjectionRbf;
import com.billcom.model.JbpmReengagement;
import com.billcom.model.MigrationBean;
import com.billcom.model.ReengagementRequest;
import com.billcom.model.RestResponse;
import com.billcom.model.UpdateGrh;
import com.billcom.model.UpdateOption;
import com.billcom.utils.BatchUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;



import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
@Slf4j
@PropertySource({ "classpath:batchConfig.properties" })
@Service
public class ReengagementService implements IReengagementService {
	private Logger log = LoggerFactory.getLogger(ReengagementService.class);
	@Autowired
	private BatchDaoImp batchDaoImp;

	@Autowired
	private BatchUtils batchUtils;

	@Value("${batch.mode}")
	String batchMode;
	
	@Value("${MessageErrorMaxLength}")
	Integer MessageErrorMaxLength;
	
	@Override
	public void updateReengagementRequest(String jbpmRef, Long orderId, Integer status, String errorMessage,
			String entryDate) {
		if(errorMessage != null ) {
			if(errorMessage.length()>MessageErrorMaxLength) {
				errorMessage = errorMessage.substring(0,MessageErrorMaxLength); 
			}
		}
		
		log.info("updateReengagementRequest -> jbpmRef ::" + jbpmRef + " , orderId ::  " + orderId + " , date :: "
				+ entryDate + " , status :: " + status + " , error ::" + errorMessage);
		batchDaoImp.updateStatus(jbpmRef, orderId, status, errorMessage, entryDate);
	}

	@Override
	public BatchBean batchModeTraitement(ReengagementRequest reengagementRequestList) throws Exception {
		BatchBean beanBatch = new BatchBean();
		RestResponse restResponse = new RestResponse();
		try {
			if (reengagementRequestList != null) {
				beanBatch = startReengagementFromJsonRequest(reengagementRequestList);
			}
			log.info("beanBatch Response = " + beanBatch.getRestResponse().isIssuccessful());
		} catch (Exception e) {
			String errorMessage = "Error message :: " + e.getMessage();
			log.info("batchModeTraitement error message ::  " + errorMessage);
			restResponse.setIssuccessful(false);
			restResponse.setComment(errorMessage);
			beanBatch.setRestResponse(restResponse);
			log.error("error message :: " + e.getMessage());
			e.printStackTrace();
			log.info("isIssuccessful :: " + beanBatch.getRestResponse().isIssuccessful());
			throw e;
		}

		return beanBatch;
	}

	@Override
	public int getRetryNumber(String jbpmRef) {
		int retryNumber = batchDaoImp.getRetryNumber(jbpmRef);
		return retryNumber;
	}

	@Override
	public void updateRetryNumber(String jbpmRef, int retryNumber) {
		batchDaoImp.updateRetryNumber(jbpmRef, retryNumber);
	}

	private BatchBean startReengagementFromJsonRequest(ReengagementRequest reengagementRequest) throws Exception {
	    BatchBean response = new BatchBean();
	    String migration = reengagementRequest.getMIGARATION();
	    
	    String updateOption = reengagementRequest.getUPDATEOPTION();
	    String updateGrh = reengagementRequest.getUPDATEGRH();
	    String injectionRbf = reengagementRequest.getINJECTIONRBF();
	    String injectionGps = reengagementRequest.getINJECTIONGPS();
	    MigrationBean[] migrationBean = null;
	    UpdateOption[] updateOptionTab = null;
	    UpdateGrh updateGrhObject = null;
	    InjectionRbf[] injectionRbfObject = null;
	    InjectionGps injectionGpsObject = null;
	    JbpmReengagement jbpmReengagement = new JbpmReengagement();
	    ObjectMapper objectMapper = new ObjectMapper();
	    log.info("MigrationBean" +migration);
	    if (migration != null) {
	    	
	      migrationBean = (MigrationBean[])objectMapper.readValue(migration, MigrationBean[].class);
	      log.info("Error Casting " +migration);
	      jbpmReengagement.setMigration(Arrays.asList(migrationBean));
	    } 
	    if (updateOption != null) {
	      updateOptionTab = (UpdateOption[])objectMapper.readValue(updateOption, UpdateOption[].class);
	      jbpmReengagement.setUpdateOption(Arrays.asList(updateOptionTab));
	    } 
	    log.info("updateGrh " + updateGrh);
	    if (updateGrh != null) {
	      updateGrhObject = (UpdateGrh)objectMapper.readValue(updateGrh, UpdateGrh.class);
	      jbpmReengagement.setUpdateGrh(updateGrhObject);
	    } 
	    if (injectionRbf != null) {
	      injectionRbfObject = (InjectionRbf[])objectMapper.readValue(injectionRbf, InjectionRbf[].class);
	      jbpmReengagement.setInjectionRbf(Arrays.asList(injectionRbfObject));
	    } 
	    if(injectionGps != null) {
	    if (!injectionGps.toString().equalsIgnoreCase("null")) {
	    	
	    	JSONObject obj = new JSONObject(injectionGps);
			  String amount = obj.getJSONObject("cagnotte").getString("amount");  
			  String cagnotteHT = obj.getJSONObject("cagnotte").getString("cagnotteHT");
			  String cagnotteHtFacilite= obj.getJSONObject("cagnotte").getString("cagnotteHtFacilite");
			  log.info("----------------*************-----------");
			  log.info("amount" +amount + "cagnotteHT" +cagnotteHT +"cagnotteHtFacilite" +cagnotteHtFacilite);
			  String newAmount="" ;
			  String newcagnotteHT=""; 
			  String newcagnotteHtFacilite="" ;
			  
			  if(amount.contains(",")) {
				  newAmount = amount.replace("," , ".");
				  log.info("newAmount" +newAmount);
				  injectionGps = injectionGps.replace(amount, newAmount); 
			  }
			
			  if(cagnotteHT.contains(",")) {
				  newcagnotteHT = cagnotteHT.replace("," , ".");
				  log.info("newcagnotteHT" +newcagnotteHT);
				  injectionGps=  injectionGps.replace(cagnotteHT, newcagnotteHT); 
			  }
			  if(cagnotteHtFacilite.contains(",")) {
				  newcagnotteHtFacilite = cagnotteHtFacilite.replace("," , ".");
				  log.info("newcagnotteHtFacilite" +newcagnotteHtFacilite);
				  injectionGps = injectionGps.replace(cagnotteHtFacilite, newcagnotteHtFacilite);
			  }
	    	
	      injectionGpsObject = (InjectionGps)objectMapper.readValue(injectionGps, InjectionGps.class);
	      jbpmReengagement.setInjectionGps(injectionGpsObject);
	    } 
	    }
	    jbpmReengagement.setCanal(reengagementRequest.getCANAL());
	    jbpmReengagement.setCustId(reengagementRequest.getCUST_ID());
	    jbpmReengagement.setDuedate(reengagementRequest.getDUEDATE());
	    jbpmReengagement.setOsmReference(reengagementRequest.getOSMREFERENCE());
	    jbpmReengagement.setJbpmrefreegagement(reengagementRequest.getJBPM_Ref_Rengagement());
	
	    response = this.batchUtils.consumeRestApiPostStartReengagementJbpm(jbpmReengagement);
	    return response;
	    
	  }

}
