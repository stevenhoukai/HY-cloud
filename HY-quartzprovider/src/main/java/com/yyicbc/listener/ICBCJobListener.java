package com.yyicbc.listener;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ICBCJobListener implements JobListener {

    private final static Logger logger= LoggerFactory.getLogger(ICBCJobListener.class);

    @Override
    public String getName() {
        return "MyJobListener";
    }

    /**
     * (1)
     * 任务执行之前执行
     * Called by the Scheduler when a JobDetail is about to be executed (an associated Trigger has occurred).
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
//        Trigger trigger = jec.getTrigger();
        logger.info("task : "+jobDetail.getJobClass()+" 准备执行任务");

    }

    /**
     * (2)
     * 这个方法正常情况下不执行,但是如果当TriggerListener中的vetoJobExecution方法返回true时,那么执行这个方法.
     * 需要注意的是 如果方法(2)执行 那么(1),(3)这个俩个方法不会执行,因为任务被终止了嘛.
     * Called by the Scheduler when a JobDetail was about to be executed (an associated Trigger has occurred),
     * but a TriggerListener vetoed it's execution.
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
//        System.out.println("MyJobListener.jobExecutionVetoed()");
//        JobDetail jobDetail = context.getJobDetail();
//        logger.info("task : "+jobDetail.getClass()+" before executed");
    }

    /**
     * (3)
     * 任务执行完成后执行,jobException如果它不为空则说明任务在执行过程中出现了异常
     * Called by the Scheduler after a JobDetail has been executed, and be for the associated Trigger's triggered(xx) method has been called.
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context,
            JobExecutionException jobException) {
        JobDetail jobDetail = context.getJobDetail();
        if(jobException!=null){
            logger.error("task : "+jobDetail.getJobClass()+"执行失败，存在异常 :"+jobException.getMessage());
        }else{
            logger.info("task : "+jobDetail.getJobClass()+" 执行成功");
        }
    }

}