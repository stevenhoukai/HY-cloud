package com.yyicbc.job;

import com.yyicbc.service.FovaReturnDataService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

@Slf4j
@DisallowConcurrentExecution
public class FovaReturnJob extends QuartzJobBean {

    @Resource
    FovaReturnDataService fovaReturnDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取参数
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        // 业务逻辑 ...
        log.info("fovaRet Job start");
        try {
            fovaReturnDataService.saveReturnFovaData();
        }catch (Exception e){
            log.error("fovaRet Job exception : "+e.getMessage());
            throw new JobExecutionException(e.getMessage());
        }
        log.info("fovaRet Job end");
    }
}
