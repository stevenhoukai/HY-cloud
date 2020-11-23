package com.yyicbc.jobservice.Impl;

import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.imports.PO.DsfAccnoPO;
import com.yyicbc.beans.utils.distribute.IdWorker;
import com.yyicbc.service.DsfAccnoService;
import com.yyicbc.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameDsfAccnoServiceImpl
 * @Description
 * @Author vic
 * @Date2020/3/11 9:49
 * @Version V1.0
 **/
@Slf4j
@Service
public class DsfAccnoServiceImpl implements DsfAccnoService {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    IdWorker idWorker;

    /**
     * 功能描述: 获取账号
     * <p>
     * select accno 
     * from ( 
     *         select *, case when accno=SUBSTRING('0119100100000376143',12,19) then accno 
     *                                    when SUBSTRING(accno,5,15)=SUBSTRING('0119100100000376143',16,15) then accno 
     *                                    when MACCNO=SUBSTRING('0119100100000376143',18,13) then accno 
     *                           end as seq       
     *         from NR0103_INS_1D 
     *         where (accno=SUBSTRING('0119100100000376143',12,19) 
     *                    or SUBSTRING(accno,5,15)=SUBSTRING('0119100100000376143',16,15)   
     *                    or MACCNO=SUBSTRING('0119100100000376143',18,13) 
     *
     * @param
     * @return: {@link Map}
     * @Author: vic
     * @Date: 2020/3/11 10:03
     */
    @Override
    public Map findZeroMap() {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void importDsfAccno(Map paramMap) {

    }



    /**
     * 功能描述: 批量插入，数据平均70万条
     *
     * @param dsfList
     * @return:
     * @Author: vic
     * @Date: 2020/3/18 10:07
     */
    private void batchInsert(List<DsfAccnoPO> dsfList) {

        if (CollectionUtils.isNotEmpty(dsfList)) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO nr0103_ins_1d(id,datadate,name,accno,maccno) VALUES ");
            for (DsfAccnoPO dsfAccnoPO : dsfList) {
                sb.append("(?,?,?,?,?),");
            }
            String sql = sb.toString().substring(0, sb.length() - 1);
            Query query = entityManager.createNativeQuery(sql);
            int paramIndex = 0;
            for (DsfAccnoPO dsfAccnoPO : dsfList) {
                query.setParameter(++paramIndex, idWorker.nextId());
                query.setParameter(++paramIndex, dsfAccnoPO.getDatadate());
                query.setParameter(++paramIndex, dsfAccnoPO.getName());
                query.setParameter(++paramIndex, dsfAccnoPO.getAccno());
                query.setParameter(++paramIndex, dsfAccnoPO.getMaccno());
            }
            query.executeUpdate();

        }
    }


}
