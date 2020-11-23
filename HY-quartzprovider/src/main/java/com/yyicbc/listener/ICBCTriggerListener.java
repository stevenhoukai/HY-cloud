package com.yyicbc.listener;


import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ICBCTriggerListener implements TriggerListener {
    
    private final static Logger logger= LoggerFactory.getLogger(ICBCTriggerListener.class);

    /**  
     * @see org.quartz.TriggerListener#getName()    
     */
    @Override
    public String getName() {
        return "MonitorTriggerListener";
    }
    
    /**  
     * @see org.quartz.TriggerListener#triggerComplete(org.quartz.Trigger, org.quartz.JobExecutionContext, int)    
     */
//    @Override
//    public void triggerComplete(Trigger arg0, JobExecutionContext jec, int arg2) {
//        try {
//            QuartzLog quartzLog=localLog.get();
//            if(quartzLog==null)return ;
//            quartzLog.setExeTime(jec.getJobRunTime());
//            getService().update(quartzLog);
//        } catch (Exception e) {
//            logger.error("记录job结束时间异常",e);
//        }catch (Throwable e) {
//            logger.error("记录job结束时间出错",e);
//        }
//    }
    
    /**  
     * @see org.quartz.TriggerListener#triggerFired(org.quartz.Trigger, org.quartz.JobExecutionContext)    
     */
    @Override
    public void triggerFired(Trigger arg0, JobExecutionContext jec) {
        JobDetail jobDetail = jec.getJobDetail();
//        Trigger trigger = jec.getTrigger();
        logger.info("trigger : "+jobDetail.getJobClass()+" 触发准备开始");
    }
    
    /**  
     * @see org.quartz.TriggerListener#triggerMisfired(org.quartz.Trigger)    
     */
    @Override
    public void triggerMisfired(Trigger arg0) {
        String name = arg0.getJobKey().getName();
        logger.info("trigger : "+name+" misfired");

    }


    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        JobDetail jobDetail = context.getJobDetail();
        logger.info("trigger : "+jobDetail.getJobClass()+" 触发结束");

    }

    /**  
     * @see org.quartz.TriggerListener#vetoJobExecution(org.quartz.Trigger, org.quartz.JobExecutionContext)    
     */
    @Override
    public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
        return false;
    }
//    private QuartzLogService getService(){
//        return (QuartzLogService)BeanLocator.getBeanInstance("quartzLogService");
//    }
}
