package com.yyicbc.job;

import com.yyicbc.beans.utils.CCustomDate;
import com.yyicbc.service.FovaReturnDataService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@DisallowConcurrentExecution
public class FovaJob extends QuartzJobBean {

    @Resource
    FovaReturnDataService fovaReturnDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取参数
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        // 业务逻辑 ...
        log.info("fova Job start");
        try {
            //add by fmm 如果为空按系统默认日期，反之有配置参数日期
            Map paramMap = new HashMap();
            paramMap.put("runNumber", jobDataMap.get("0"));
            paramMap.put("updateDate", jobDataMap.get("1"));
            fovaReturnDataService.updateFovaData(paramMap);
        }catch (Exception e){
            log.error("fova Job exception : "+e.getMessage());
            throw new JobExecutionException(e.getMessage());
        }
        log.info("fova Job end");
    }
}
