package com.yyicbc;

import com.yyicbc.beans.utils.distribute.IdWorker;
import com.yyicbc.listener.ICBCJobListener;
import com.yyicbc.listener.ICBCTriggerListener;
import org.quartz.Scheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class HYQuartzproviderApplication {


    public static void main(String[] args) {
        SpringApplication.run(HYQuartzproviderApplication.class, args);
    }


    /**
     *
     * @param scheduler
     * @return
     * @throws Exception
     */
    @Bean
    public Scheduler scheduler(Scheduler scheduler) throws Exception {
        // 添加Scheduler 监听器
//        scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());
//        // 添加JobListener, 精确匹配JobKey
//        KeyMatcher<JobKey> keyMatcher = KeyMatcher.keyEquals(JobKey.jobKey("helloJob", "group1"));
//        scheduler.getListenerManager().addJobListener(new HelloJobListener(), keyMatcher);
        scheduler.getListenerManager().addTriggerListener(new ICBCTriggerListener());
        scheduler.getListenerManager().addJobListener(new ICBCJobListener());
        return scheduler;
    }


    //分布式主键生成器
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(2,1);
    }
}