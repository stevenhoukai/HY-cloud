package com.yyicbc.controller;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.querycondition.TaskQueryVO;
import com.yyicbc.beans.task.CronTaskVO;
import com.yyicbc.beans.task.SimpleTaskVO;
import com.yyicbc.service.QuartzService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/*
 * @author stevenHou
 * @date 2020/10/14 : 18:36
 * @class QuartzController
 * @description
 * @desc:
 * 后台任务管理核心接口类
 * 主要实现对任务的新增、修改、删除、暂停、继续、立即执行等功能
 */
@RestController
@RequestMapping(value = "/system/task",produces = {"application/json;charset=UTF-8"} )
public class QuartzController {

    @Autowired
    QuartzService quartzService;

    /*
     * @author stevenHou
     * @date 2020/10/14 : 18:36
     * @param  * @param page
     * @return com.yyicbc.beans.RetData
     * @desc 列表
     */
    @GetMapping("/list")
    public RetData getAllTasks(@RequestParam Integer page) throws SchedulerException {
        TaskQueryVO vo = new TaskQueryVO();
        vo.setPage(page);
        RetData result = quartzService.findAll(vo);
        return result;
    }

    /*
     * @author stevenHou
     * @date 2020/10/14 : 18:39
     * @class QuartzController 
     * @description 運行中列表
     */
    @GetMapping("/runninglist")
    public RetData getAllRunningTasks(@RequestParam Integer page) throws SchedulerException {
        TaskQueryVO vo = new TaskQueryVO();
        vo.setPage(page);
        RetData result = quartzService.findAllRunning(vo);
        return result;
    }


    /*
     * @author stevenHou
     * @date 2020/10/14 : 18:41
     * @class QuartzController
     * @description 暫停
     */
    @PostMapping("/pause")
    public RetData pauseTask(@RequestParam String taskId) throws SchedulerException {
        quartzService.pauseJob(taskId.split("&")[0],taskId.split("&")[1]);
        RetData retData = new RetData();
        retData.setCode(StatusCode.OK).setMsg("暂停成功");
        return retData;
    }


    /*
     * @author stevenHou
     * @date 2020/10/14 : 18:42
     * @param  * @param taskId
     * @return com.yyicbc.beans.RetData
     * @desc 繼續執行任務
     */
    @PostMapping("/resume")
    public RetData resumeTask(@RequestParam String taskId) throws SchedulerException{
        quartzService.resumeJob(taskId.split("&")[0],taskId.split("&")[1]);
        RetData retData = new RetData();
        retData.setCode(StatusCode.OK).setMsg("开启成功");
        return retData;
    }


    /*
     * @author stevenHou
     * @date 2020/10/14 : 18:42
     * @param  * @param taskId
     * @return com.yyicbc.beans.RetData
     * @desc 開啟任務
     */
    @PostMapping("/run")
    public RetData runTask(@RequestParam String taskId) throws SchedulerException{
        quartzService.runAJobNow(taskId.split("&")[0],taskId.split("&")[1]);
        RetData retData = new RetData();
        retData.setCode(StatusCode.OK).setMsg("任务运行成功");
        return retData;
    }


    /*
     * @author stevenHou
     * @date  2020/10/14 : 18:39
     * @param  * @param task
     * @return com.yyicbc.beans.RetData
     * @desc 新增simple任務
     */
    @PostMapping(value = "/simple/add")
    public RetData saveSimpleTask(SimpleTaskVO task) throws SchedulerException, ClassNotFoundException {
        RetData retData = quartzService.addSimpleJob(task);
        return retData;
    }

    /*
     * @author stevenHou
     * @date  2020/10/14 : 18:39
     * @param  * @param task
     * @return com.yyicbc.beans.RetData
     * @desc 更新simple任務
     */
    @PostMapping(value = "/simple/update",name = "/system/task/simple/update")
    public RetData updateSimpleTask(SimpleTaskVO task)throws SchedulerException,ClassNotFoundException{
        RetData retData = quartzService.updateSimpleJob(task);
        return retData;
    }

    /*
     * @author stevenHou
     * @date  2020/10/14 : 18:39
     * @param  * @param task
     * @return com.yyicbc.beans.RetData
     * @desc 刪除simple任務
     */
    @PostMapping(value = "/delete",name = "/system/task/delete")
    public RetData deleteTask(SimpleTaskVO simpleTaskVO)throws SchedulerException{
        RetData retData = quartzService.deleteTask(simpleTaskVO);
        return retData;
    }


    /*
     * @author stevenHou
     * @date  2020/10/14 : 18:39
     * @param  * @param task
     * @return com.yyicbc.beans.RetData
     * @desc 新增cron
     */
    @PostMapping(value = "/cron/add",name = "/system/task/cron/add")
    public RetData saveCronTask(CronTaskVO task) throws ClassNotFoundException,SchedulerException {
        RetData retData = quartzService.addCronJob(task);
        return retData;
    }


    /*
     * @author stevenHou
     * @date  2020/10/14 : 18:39
     * @param  * @param task
     * @return com.yyicbc.beans.RetData
     * @desc 更新cron
     */
    @PostMapping(value = "/cron/update",name = "/system/task/cron/update")
    public RetData updateCronTask(CronTaskVO task)throws SchedulerException,ClassNotFoundException{
        RetData retData = quartzService.updateCronJob(task);
        return retData;
    }


}
