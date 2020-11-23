package com.yyicbc.job;

import com.yyicbc.beans.business.PO.FovaReturnTempPO;
import com.yyicbc.service.FovaReturnTempDataService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vic fu
 * <p>
 * 定时执行，处理中间表的数据
 * 中间表记录的内容：后台任务调用ICBC提供的接口，保存到这个中间表（记录发送日期不等于交易日期的数据）
 */

@Slf4j
@DisallowConcurrentExecution
public class FovaReturnTempJob extends QuartzJobBean {

    @Resource
    FovaReturnTempDataService fovaReturnTempDataService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 获取参数
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        // 业务逻辑 ...
        log.info("fovaRetTemp Job start");
        try {
            //获取参数
            Map paramMap = new HashMap();
            paramMap.put("senddate", jobDataMap.get("0"));
            /*paramMap.put("updateDate", jobDataMap.get("1"));*/

            log.info("保存接口数据");
            List<FovaReturnTempPO> t1data = fovaReturnTempDataService.saveTempData(paramMap);
            log.info("更新fova数据");
            fovaReturnTempDataService.updateFovaDataByTemp(t1data);


        } catch (Exception e) {
            log.error("fovaRetTemp Job exception : " + e.getMessage());
            throw new JobExecutionException(e.getMessage());
        }
        log.info("fovaRetTemp Job end");
    }
}
