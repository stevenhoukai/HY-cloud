package com.yyicbc.job;

import com.yyicbc.jobservice.Impl.HcmServiceImpl;
import com.yyicbc.service.HcmService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

@Slf4j
@DisallowConcurrentExecution
public class HcmJob extends QuartzJobBean {

    @Resource
    HcmService hcmService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        log.info("Hcm Job start");
        try {
            hcmService.sendCompensationData();
        }catch (Exception e){
            log.error("Hcm Job exception : "+e.getMessage());
            throw new JobExecutionException(e.getMessage());
        }
        log.info("Hcm Job end");
    }
}
