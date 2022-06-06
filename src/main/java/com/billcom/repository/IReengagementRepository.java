package com.billcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.billcom.model.ReengagementRequest;

@Repository
public interface IReengagementRepository extends JpaRepository<ReengagementRequest, String> {

	
	@Modifying
	@Query(value = ("UPDATE REENGAGEMENT_REQUEST SET STATUS=:status , ORDER_ID =if(:orderId is null, ORDER_ID, :orderId) , ERROR_MESSAGE =if(:errorMessage is null, ERROR_MESSAGE, :errorMessage), ENTRY_DATE =if(:entryDate is null, ENTRY_DATE, :entryDate) WHERE JBPM_Ref_Rengagement = :jbpmRefReengagement "), nativeQuery = true)
	void updateRequestStatus(@Param(value = "status") int status, @Param(value = "jbpmRefReengagement") String jbpmRef,
			@Param(value = "orderId") Long orderId, @Param(value = "errorMessage") String errorMessage,
			@Param(value = "entryDate") String entryDate);

	@Query(value = "SELECT RETRY_NUMBER FROM REENGAGEMENT_REQUEST WHERE JBPM_Ref_Rengagement =:jbpmRef ", nativeQuery = true)
	int getRetryNumber(@Param(value = "jbpmRef") String jbpmRef);


	@Modifying
	@Query(value = "UPDATE REENGAGEMENT_REQUEST SET RETRY_NUMBER =:retryNumber  WHERE JBPM_Ref_Rengagement =:jbpmRef ", nativeQuery = true)
	void updateRetryNumber(@Param(value = "jbpmRef") String jbpmRef,
			@Param(value = "retryNumber") int retryNumber);
	
	
	@Query(value = "SELECT MIN(ID) FROM REENGAGEMENT_REQUEST where STATUS = :status ", nativeQuery = true)
	Long getIdMin(@Param(value = "status") int status);
	
	@Query(value = "SELECT MAX(ID) FROM REENGAGEMENT_REQUEST where STATUS = :status ", nativeQuery = true)
	Long getIdMax(@Param(value = "status") int status);
	
	@Transactional
	@Query(value = ("UPDATE REENGAGEMENT_REQUEST SET STATUS=:status  WHERE ID >= :minValue AND ID <= :maxValue "), nativeQuery = true)
	@Modifying
	void updateStatus(@Param(value = "status") int status, @Param(value = "minValue") Long minValue ,  @Param(value = "maxValue") Long maxValue);
	

}
