package com.billcom.partitioner;


import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.*;
import com.billcom.dao.BatchDaoImp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RangePartitionerForRecycle implements Partitioner 
{
	
	private Logger log = LoggerFactory.getLogger(RangePartitionerForRecycle.class);

	@Autowired
	private BatchDaoImp batchDaoImp;

	@Value("${request.status.recycle}")
	int recycle;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) 
	{
		log.info("=> RangePartitionerForRecycle IN"  );
	
		Long min = batchDaoImp.getIdMin(recycle);
		Long max = batchDaoImp.getIdMax(recycle);
		
		log.info("min of partion = "+min+" max of partion = "+max);
	
		Map<String, ExecutionContext> result = new HashMap<>();
		if ((min != null) && (max != null)) {
			Long targetSize = (max - min) / gridSize + 1;
			int number = 0;
			Long start = min;
			Long end = start + targetSize - 1;
			
			while (start <= max) 
				
			{
				ExecutionContext value = new ExecutionContext();
				System.out.println("number =" +number+" value "+value.toString() );
				result.put("partition" + number, value);
				
				if(end >= max) {
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
	}
}