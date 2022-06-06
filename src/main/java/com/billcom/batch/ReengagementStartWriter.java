package com.billcom.batch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.slf4j.*;

import com.billcom.model.BatchBean;
import com.billcom.service.ReengagementService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource({ "classpath:batchConfig.properties" })
public class ReengagementStartWriter implements ItemWriter<BatchBean> {
	private Logger log = LoggerFactory.getLogger(ReengagementStartWriter.class);

	@Autowired
	private ReengagementService reengagementService;

	@Value("${request.status.recycle}")
	Integer statusRecycle;

	@Value("${request.status.completed}")
	Integer statusCompleted;
	
	

	private String jbpmRef = null;
	private long orderId = 0;
	private String date = null;
	private Integer status = null;
	private String error = null;

	@Override
	public void write(List<? extends BatchBean> items) throws Exception {
		log.info("=> ReengagementStartWriter IN " + items.size());
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			items.stream().forEach(itemss -> {
				jbpmRef = itemss.getReengagementRequest().getJBPM_Ref_Rengagement();
				if (itemss.getRestResponse().isIssuccessful()) {
					log.info("Update request Issuccessful :: true with JBPM_Ref = " + jbpmRef);
					orderId = itemss.getRestResponse().getId();
					status = statusCompleted;
					error = itemss.getRestResponse().getComment();									
					Date entryDate = new Date();
					date = formatter.format(entryDate);
					log.info("updateReengagementRequest for start true -> jbpmRef ::" + jbpmRef + " , orderId ::  " + orderId + " , date :: "
							+ date + " , status :: " + status + " , error ::" + error);
					reengagementService.updateReengagementRequest(jbpmRef, orderId, status, error, date);

				} else {
					log.info("Update request Issuccessful :: false with JBPM_Ref = " + jbpmRef);
					status = statusRecycle;
					error = itemss.getRestResponse().getComment();
					Date entryDate = new Date();
					date = formatter.format(entryDate);
					orderId = itemss.getRestResponse().getId();
					log.info("updateReengagementRequest for start false -> jbpmRef ::" + jbpmRef + " , orderId ::  " + orderId + " , date :: "
							+ date + " , status :: " + status + " , error ::" + error);
					reengagementService.updateReengagementRequest(jbpmRef, orderId, status, error, date);
				}

			});
		} catch (Exception e) {
			log.error("error :: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
