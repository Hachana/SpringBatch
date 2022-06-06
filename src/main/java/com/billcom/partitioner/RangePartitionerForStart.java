package com.billcom.partitioner;

import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.billcom.dao.BatchDaoImp;
import com.billcom.repository.IReengagementRepository;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
@Slf4j
@EnableTransactionManagement
public class RangePartitionerForStart implements Partitioner {
	private Logger log = LoggerFactory.getLogger(RangePartitionerForStart.class);
	@Autowired
	private BatchDaoImp batchDaoImp;

	@Value("${request.status.initial}")
	int statusInitial;

	@Autowired
	private IReengagementRepository reengagementRepository;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		log.info("=> RangePartitionerForStart IN");

		Long min = batchDaoImp.getIdMin(statusInitial);
		Long max = batchDaoImp.getIdMax(statusInitial);

		log.info("min of partion = " + min + " max of partion = " + max);
		Map<String, ExecutionContext> result = new HashMap<>();
		if ((min != null) && (max != null)) {
			Long targetSize = (max - min) / gridSize + 1;

			int number = 0;
			Long start = min;
			Long end = start + targetSize - 1;

			while (start <= max)

			{
				ExecutionContext value = new ExecutionContext();
				log.info("number =" + number + " value " + value.toString());
				result.put("partition" + number, value);

				if (end >= max) {
					end = max;
				}

				value.putLong("minValue", start);
				value.putLong("maxValue", end);
				log.info("minValue =" + start + " maxValue " + end);
				reengagementRepository.updateStatus(2, start, end);
				start += targetSize;
				end += targetSize;

				number++;
			}

		}
		return result;

	}
	
	//Get minValue && maxValue depend a gridSize
	/*@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		log.info("=> RangePartitionerForStart IN");

		Long min = batchDaoImp.getIdMin(statusInitial);
		Long max = batchDaoImp.getIdMax(statusInitial);

		log.info("min of partion = " + min + " max of partion = " + max);
		Map<String, ExecutionContext> result = new HashMap<>();
		if ((min != null) && (max != null)) {
			Long targetSize = (max - min) / gridSize + 1;

			int number = 0;
			Long start = min;
			Long end = start + targetSize - 1;

			while (start <= max)

			{
				ExecutionContext value = new ExecutionContext();
				log.info("number =" + number + " value " + value.toString());
				result.put("partition" + number, value);

				if (end >= max) {
					end = max;
				}

				value.putLong("minValue", start);
				value.putLong("maxValue", end);

				start += targetSize;
				end += targetSize;

				number++;
			}

		}
		return result;

	}*/
}