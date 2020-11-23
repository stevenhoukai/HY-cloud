package com.yyicbc.job;

import com.yyicbc.jobservice.TestService;
import com.yyicbc.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.support.TransactionTemplate;


/***
 * 测试job类
 */
@Slf4j
@DisallowConcurrentExecution
public class Job extends QuartzJobBean {

    @Autowired
    TestService testService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取参数
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

//        String index1  = (String) jobDataMap.get("0");
//        String index2  = (String) jobDataMap.get("1");
//        String index3  = (String) jobDataMap.get("2");
//        System.out.println(index1);
//        System.out.println(index2);
//        System.out.println(index3);
        // 业务逻辑 ...
        System.out.println("job start");
        try {
            testService.test1();
        } catch (Exception e) {
            e.printStackTrace();
            throw new JobExecutionException(e.getMessage());
        }
        System.out.println("job end");
    }
}