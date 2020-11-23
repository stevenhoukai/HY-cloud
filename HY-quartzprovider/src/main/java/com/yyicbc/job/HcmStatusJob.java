package com.yyicbc.job;

import com.yyicbc.service.HcmService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import javax.annotation.Resource;

@Slf4j
@DisallowConcurrentExecution
public class HcmStatusJob  extends QuartzJobBean
{
    @Resource
    HcmService hcmService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("HcmStatus Job Start!");
        hcmService.sendMainStatus();

        log.info("HcmStatus Job End!");

    }
}
