package com.billcom;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler {
	private Logger log = LoggerFactory.getLogger(Scheduler.class);
    @Autowired
   private BatchLauncher batchLauncher;
    
    DataSource dataSource;
    

    @Scheduled(cron="${launch.cron}")
    public void perform() throws Exception {
       log.info("---> Batch programmed to run every 10 seconds !");
        batchLauncher.run();
         }
}