package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.querycondition.TaskQueryVO;
import com.yyicbc.beans.task.CronTaskVO;
import com.yyicbc.beans.task.SimpleTaskVO;
import com.yyicbc.pagehelper.JpaPageHelper;
import com.yyicbc.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * 描述:
 * 后台任务管理核心服务类
 * 主要实现对任务的新增、修改、删除、暂停、继续、立即执行等功能
 * @author stv
 */
@Slf4j
@Service
public class QuartzService {


    private Integer Common_page_size = 10;

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    /**
     * 增加一个job simple
     *
     * @param jobClass     任务实现类
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     * @param jobTime      时间表达式 (这是每隔多少秒为一次任务)
     * @param jobTimes     运行的次数 （<0:表示不限次数）
     * @param jobDesc      任务描述
     * @param jobData      参数
     */
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime,
                       int jobTimes,String jobDesc, Map jobData,Date startTime,Date endTime)throws SchedulerException {
        try {
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).withDescription(jobDesc)
                    .build();
            // 设置job参数
            if (jobData != null && jobData.size() > 0) {
                jobDetail.getJobDataMap().putAll(jobData);
            }
            // 使用simpleTrigger规则
            Trigger trigger = null;
            if (jobTimes < 0) {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName).withDescription(jobDesc)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime))
                        .startAt(startTime).endAt(endTime).build();
            } else {
                trigger = TriggerBuilder
                        .newTrigger().withIdentity(jobName, jobGroupName).withDescription(jobDesc).withSchedule(SimpleScheduleBuilder
                                .repeatSecondlyForever(1).withIntervalInSeconds(jobTime).withRepeatCount(jobTimes))
                        .startAt(startTime).endAt(endTime).build();
            }
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 增加一个job cron
     *
     * @param jobClass     任务实现类
     * @param jobName      任务名称(建议唯一)
     * @param jobGroupName 任务组名
     * @param jobTime      时间表达式 （如：0/5 * * * * ? ）
     * @param jobDesc      任务描述
     * @param jobData      参数
     */
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime, String jobDesc,Map jobData,
                       Date startTime,Date endTime)throws SchedulerException {
        try {
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).withDescription(jobDesc)
                    .build();
            // 设置job参数
            if (jobData != null && jobData.size() > 0) {
                jobDetail.getJobDataMap().putAll(jobData);
            }
            // 定义调度触发规则
            // 使用cornTrigger规则
            // 触发器key
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName).withDescription(jobDesc)
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).startAt(startTime).endAt(endTime)
                            .build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 修改 一个job的 时间表达式
     *
     * @param jobName
     * @param jobGroupName
     * @param jobTime
     */
    public void updateJob(String jobName, String jobGroupName, String jobTime,
                          String jobDesc, Map jobData,Date startTime,Date endTime,String jobclass) throws SchedulerException,ClassNotFoundException{
        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            Trigger originTrigger = scheduler.getTrigger(triggerKey);
            if(originTrigger==null){
                throw new SchedulerException("任务名称不可修改!");
            }
            Class classobj=Class.forName(jobclass);

            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
            if(originTrigger instanceof CronTrigger){

                JobDetail jobDetail = JobBuilder.newJob(classobj).withIdentity(jobName, jobGroupName).withDescription(jobDesc)
                        .build();
                // 设置job参数
                if (jobData != null && jobData.size() > 0) {
                    jobDetail.getJobDataMap().putAll(jobData);
                }
                CronTrigger trigger = (CronTrigger) originTrigger;
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withDescription(jobDesc)
                        .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).startAt(startTime).endAt(endTime).build();

                // 重启触发器
                scheduler.scheduleJob(jobDetail,trigger);
//                scheduler.rescheduleJob(triggerKey, trigger);
            }else if(originTrigger instanceof SimpleTrigger){

                JobDetail jobDetail = JobBuilder.newJob(classobj).withIdentity(jobName, jobGroupName).withDescription(jobDesc)
                        .build();
                // 设置job参数
                if (jobData != null && jobData.size() > 0) {
                    jobDetail.getJobDataMap().putAll(jobData);
                }
                SimpleTrigger trigger = (SimpleTrigger) originTrigger;
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withDescription(jobDesc)
                        .withSchedule(SimpleScheduleBuilder
                                .repeatSecondlyForever(1).withIntervalInSeconds(Integer.parseInt(jobTime))).startAt(startTime).endAt(endTime).build();
                // 重启触发器
//                scheduler.rescheduleJob(triggerKey, trigger);
                scheduler.scheduleJob(jobDetail,trigger);
            }

        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 删除任务一个job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    public void deleteJob(String jobName, String jobGroupName) throws SchedulerException{
        try {
            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 暂停一个job
     *
     * @param jobName
     * @param jobGroupName
     */
//    @Transactional
    public void pauseJob(String jobName, String jobGroupName) throws SchedulerException{
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 恢复一个job
     *
     * @param jobName
     * @param jobGroupName
     */
    public void resumeJob(String jobName, String jobGroupName) throws SchedulerException{
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 立即执行一个job
     *
     * @param jobName
     * @param jobGroupName
     */
    public void runAJobNow(String jobName, String jobGroupName) throws SchedulerException{
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     */
    public List<Map<String, Object>> queryAllJob() throws SchedulerException{
        List<Map<String, Object>> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<Map<String, Object>>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobName", jobKey.getName());
                    map.put("jobGroupName", jobKey.getGroup());
                    map.put("description", trigger.getDescription());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus", triggerState.name());
                    map.put("jobClass",jobDetail.getJobClass());
                    map.put("startTime",trigger.getStartTime()==null?null:trigger.getStartTime().getTime());
                    map.put("endTime",trigger.getEndTime()==null?null:trigger.getEndTime().getTime());
                    map.put("previousFireTime",trigger.getPreviousFireTime()==null?null:trigger.getPreviousFireTime().getTime());
                    map.put("nextFireTime",trigger.getNextFireTime()==null?null:trigger.getNextFireTime().getTime());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime", cronExpression);
                        map.put("triggerType","Cron");
                    }else{
                        SimpleTrigger cronTrigger = (SimpleTrigger) trigger;
                        String simpleExpression = ""+cronTrigger.getRepeatInterval()/1000;
                        map.put("jobTime", simpleExpression);
                        map.put("triggerType","Simple");
                    }
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    if(jobDataMap!=null&&jobDataMap.size()>0){
                        String[] keys = jobDataMap.keySet().toArray(new String[]{});
                        String[] paramvalues = jobDataMap.values().toArray(new String[]{});
                        map.put("keys",keys);
                        map.put("paramvalues",paramvalues);
                    }
                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw e;
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     *
     * @return
     */
    public List<Map<String, Object>> queryRunJob() throws SchedulerException{
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<Map<String, Object>>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap<String, Object>();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", trigger.getDescription());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());
                map.put("jobClass",jobDetail.getJobClass());
//                map.put("startTime",trigger.getStartTime());
//                map.put("endTime",trigger.getEndTime());
//                map.put("previousFireTime",trigger.getPreviousFireTime());
//                map.put("nextFireTime",trigger.getNextFireTime());
                map.put("startTime",trigger.getStartTime()==null?null:trigger.getStartTime().getTime());
                map.put("endTime",trigger.getEndTime()==null?null:trigger.getEndTime().getTime());
                map.put("previousFireTime",trigger.getPreviousFireTime()==null?null:trigger.getPreviousFireTime().getTime());
                map.put("nextFireTime",trigger.getNextFireTime()==null?null:trigger.getNextFireTime().getTime());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime", cronExpression);
                    map.put("triggerType","Cron");
                }else{
                    SimpleTrigger cronTrigger = (SimpleTrigger) trigger;
                    String simpleExpression = ""+cronTrigger.getRepeatInterval()/1000;
                    map.put("jobTime", simpleExpression);
                    map.put("triggerType","Simple");
                }
                JobDataMap jobDataMap = jobDetail.getJobDataMap();
                if(jobDataMap!=null&&jobDataMap.size()>0){
                    String[] keys = jobDataMap.keySet().toArray(new String[]{});
                    String[] paramvalues = jobDataMap.values().toArray(new String[]{});
                    map.put("keys",keys);
                    map.put("paramvalues",paramvalues);
                }
                jobList.add(map);
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw e;
        }
        return jobList;
    }

    /**
     * 对外查询所有任务方法
     * @param vo
     * @return
     */
    public RetData findAll(TaskQueryVO vo) throws SchedulerException{
        Integer page = vo.getPage();
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        List<Map<String, Object>> allList = queryAllJob();
        JpaPageHelper pagehelper = new JpaPageHelper();
        final List<PageInfo> pageInfos = pagehelper.SetStartPage(allList, page, Common_page_size);
        PageInfo pageInfo = pageInfos.get(0);
        retResult.setPage(pageInfo.getPageNow()).setPage_count(pageInfo.getTotlePage())
                .setPage_size(pageInfo.getPgaeSize()).setTotal_count(allList.size())
                .setItem_list(pageInfo.getList());
        retData.setCode(StatusCode.OK).setResult(retResult);
        return retData;
    }

    /**
     * 对外查询正在运行任务方法
     * @param vo
     * @return
     */
    public RetData findAllRunning(TaskQueryVO vo) throws SchedulerException{
        Integer page = vo.getPage();
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        List<Map<String, Object>> allList = queryRunJob();
        JpaPageHelper pagehelper = new JpaPageHelper();
        final List<PageInfo> pageInfos = pagehelper.SetStartPage(allList, page, Common_page_size);
        PageInfo pageInfo = pageInfos.get(0);
        retResult.setPage(pageInfo.getPageNow()).setPage_count(pageInfo.getTotlePage())
                .setPage_size(pageInfo.getPgaeSize()).setTotal_count(allList.size())
                .setItem_list(pageInfo.getList());
        retData.setCode(StatusCode.OK).setResult(retResult);
        return retData;
    }

    private Map<String,String> getJobData(SimpleTaskVO vo){
        String[] paramvalues = vo.getParamvalues();
        Map<String,String> jobData = null;
        if(paramvalues!=null&&paramvalues.length>0){
            jobData = new HashMap<String,String>();
            for(int i = 0 ; i < paramvalues.length ; i++){
                jobData.put(i+"",paramvalues[i]);
            }
        }
        return jobData;
    }

    private Map<String,String> getJobData(CronTaskVO vo){
        String[] paramvalues = vo.getParamvalues();
        Map<String,String> jobData = null;
        if(paramvalues!=null&&paramvalues.length>0){
            jobData = new HashMap<String,String>();
            for(int i = 0 ; i < paramvalues.length ; i++){
                jobData.put(i+"",paramvalues[i]);
            }
        }
        return jobData;
    }

    public RetData addSimpleJob(SimpleTaskVO vo) throws ClassNotFoundException,SchedulerException {
        RetData retData = new RetData();
        Map<String,String> jobData = getJobData(vo);
        Class obj=Class.forName(vo.getJobClass());
        addJob(obj,vo.getJobName(),"ICBC",vo.getJobTime().intValue(),
                vo.getJobTimes().intValue(),vo.getDescription(),jobData,vo.getStartTime(),vo.getEndTime());
        retData.setCode(StatusCode.OK).setMsg("addok");
        return retData;
    }

    public RetData updateSimpleJob(SimpleTaskVO vo) throws SchedulerException,ClassNotFoundException{
        RetData retData = new RetData();
        Map<String,String> jobData = getJobData(vo);
        updateJob(vo.getJobName(),"ICBC",vo.getJobTime().intValue()+""
                ,vo.getDescription(),jobData,vo.getStartTime(),vo.getEndTime(),vo.getJobClass());
        retData.setCode(StatusCode.OK).setMsg("updateok");
        return retData;
    }

    public RetData deleteTask(SimpleTaskVO vo) throws SchedulerException{
        RetData retData = new RetData();
        deleteJob(vo.getJobName(),vo.getJobGroupName());
        retData.setCode(StatusCode.OK).setMsg("deleteok");
        return retData;
    }


    public RetData addCronJob(CronTaskVO vo) throws ClassNotFoundException,SchedulerException {
        RetData retData = new RetData();
        Map<String,String> jobData = getJobData(vo);
        Class obj=Class.forName(vo.getJobClass());
        addJob(obj,vo.getJobName(),"ICBC",vo.getJobTime()
                ,vo.getDescription(),jobData,vo.getStartTime(),vo.getEndTime());
        retData.setCode(StatusCode.OK).setMsg("addok");
        return retData;
    }

    public RetData updateCronJob(CronTaskVO vo) throws SchedulerException,ClassNotFoundException{
        RetData retData = new RetData();
        Map<String,String> jobData = getJobData(vo);
        updateJob(vo.getJobName(),"ICBC",vo.getJobTime()
                ,vo.getDescription(),jobData,vo.getStartTime(),vo.getEndTime(),vo.getJobClass());
        retData.setCode(StatusCode.OK).setMsg("updateok");
        return retData;
    }
}