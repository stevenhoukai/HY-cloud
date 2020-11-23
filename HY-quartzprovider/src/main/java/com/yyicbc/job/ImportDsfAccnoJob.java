package com.yyicbc.job;

import com.yyicbc.service.DsfAccnoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassNameImportDsfAccnoJob
 * @Description 通过excel导入DSF 财政局的银行账户
 * 1、excel为空不执行任务和操作
 * 2、excel有值先清空数据后批量新增
 * @Author vic
 * @Date2020/3/17 17:48
 * @Version V1.0
 **/
@Slf4j
@DisallowConcurrentExecution
public class ImportDsfAccnoJob  extends QuartzJobBean {

    @Resource
    DsfAccnoService dsfAccnoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 业务逻辑 ...
        log.info("DSF Job start");
        try {
            //参数
            Map paramMap = new HashMap();
            dsfAccnoService.importDsfAccno(paramMap);
        }catch (Exception e){
            log.error("DSF Job exception : "+e.getMessage());
            throw new JobExecutionException(e.getMessage());
        }
        log.info("DSF Job end");
    }
}
