package com.yyicbc.service.impl;

import com.yyicbc.Exception.BusinessException;
import com.yyicbc.beans.*;
import com.yyicbc.beans.logmanager.SysLogVO;
import com.yyicbc.beans.querycondition.DailyReportQueryVO;
import com.yyicbc.beans.querycondition.LogQueryVO;
import com.yyicbc.beans.report.DailyReportVO;
import com.yyicbc.component.pagehelper.JpaPageHelper;
import com.yyicbc.component.pagehelper.PageInfo;
import com.yyicbc.dao.DailyReportDao;
import com.yyicbc.dao.SysLogDao;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;


/***
 * @author stv
 * 日报接口实现类，切面事务管理
 */
@Service
public class DailyReportServiceImpl {

    @Autowired
    SysLogDao sysLogDao;

    @Autowired
    DailyReportDao dailyReportDao;

//    @Value("${commons.page.size}")
    private Integer Common_page_size = 300;


    public RetData all(DailyReportQueryVO vo) throws BusinessException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date begindate = new Date(Long.parseLong(vo.getHandleBeginDate()));
        String strbegintime = format.format(begindate).substring(0,10).replaceAll("-","");
        Date enddate = new Date(Long.parseLong(vo.getHandleEndDate()));
        String strendtime = format.format(enddate).substring(0,10).replaceAll("-","");
        Integer page = vo.getPage();
        String corpCode = vo.getCorpCode();
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            List<Object[]> allFQList = null;
            if(StringUtils.isBlank(corpCode)){
                allFQList = dailyReportDao.findObjectByDeductionDateFQ(strbegintime,strendtime);
            }else{
                allFQList = dailyReportDao.findObjectByDeductionDateByCorpCodeFQ(strbegintime,strendtime,corpCode);
            }
//            List<Object[]> allCSList = dailyReportDao.findObjectByDeductionDateCS(strbegintime,strendtime,corpCode);
//            allFQList.addAll(allCSList);
            List<DailyReportVO> voList=new ArrayList<DailyReportVO>();
            for(Object[] obj:allFQList){
                DailyReportVO dailyReportVO=new DailyReportVO();

                dailyReportVO.setAutopay_date(obj[0]==null?"":obj[0].toString());
                dailyReportVO.setBatch_no(obj[1]==null?"":obj[1].toString());
                dailyReportVO.setCom_code(obj[2]==null?"":obj[2].toString());
                dailyReportVO.setIcbc_ac_no(obj[3]==null?"":obj[3].toString());
                dailyReportVO.setTxn_amt(obj[4]==null?new Double(0):new Double(obj[4].toString()));
                dailyReportVO.setRec_cnt(obj[5]==null?new Double(0):new Double(obj[5].toString()));
                dailyReportVO.setTxn_ccy(obj[6]==null?"":obj[6].toString());
                dailyReportVO.setDirection(obj[7]==null?"":obj[7].toString());
                dailyReportVO.setDrcr_flag(obj[8]==null?"":obj[8].toString());
                dailyReportVO.setMaker(obj[9]==null?"":obj[9].toString());
                dailyReportVO.setChecker(obj[10]==null?"":obj[10].toString());
                dailyReportVO.setId(obj[11]==null?"":obj[11].toString());
                voList.add(dailyReportVO);
            }
            JpaPageHelper pagehelper = new JpaPageHelper();
            final List<PageInfo> pageInfos = pagehelper.SetStartPage(voList, page, Common_page_size);
            PageInfo pageInfo = pageInfos.get(0);
            retResult.setPage(pageInfo.getPageNow()).setPage_count(pageInfo.getTotlePage())
                    .setPage_size(pageInfo.getPgaeSize()).setTotal_count(voList.size())
                    .setItem_list(pageInfo.getList());
            retData.setCode(StatusCode.OK).setResult(retResult).setMsg("查询成功");
        }catch (Exception e){
            throw new BusinessException(StatusCode.ERROR,e.getMessage());
        }
        return retData;
    }


    public RetData exportExcel(List list) throws BusinessException {
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            //allList为需要导出数据
            List<Object[]> allFQList = dailyReportDao.findObjectByIdsFQ(list);
            List<Object[]> allCSList = dailyReportDao.findObjectByIdsCS(list);
            allFQList.addAll(allCSList);
            List<DailyReportVO> voList=new ArrayList<DailyReportVO>();
            for(Object[] obj:allFQList){
                DailyReportVO dailyReportVO=new DailyReportVO();
                dailyReportVO.setAutopay_date(obj[0]==null?"":obj[0].toString());
                dailyReportVO.setBatch_no(obj[1]==null?"":obj[1].toString());
                dailyReportVO.setCom_code(obj[2]==null?"":obj[2].toString());
                dailyReportVO.setIcbc_ac_no(obj[3]==null?"":obj[3].toString());
                dailyReportVO.setTxn_amt(obj[4]==null?new Double(0):new Double(obj[4].toString()));
                dailyReportVO.setRec_cnt(obj[5]==null?new Double(0):new Double(obj[5].toString()));
                dailyReportVO.setTxn_ccy(obj[6]==null?"":obj[6].toString());
                dailyReportVO.setDirection(obj[7]==null?"":obj[7].toString());
                dailyReportVO.setDrcr_flag(obj[8]==null?"":obj[8].toString());
                dailyReportVO.setMaker(obj[9]==null?"":obj[9].toString());
                dailyReportVO.setChecker(obj[10]==null?"":obj[10].toString());
                dailyReportVO.setId(obj[11]==null?"":obj[11].toString());
                dailyReportVO.setUpdatestatus(obj[12]==null?"":obj[12].toString());
                voList.add(dailyReportVO);
            }
            retResult.setItem_list(voList);
            retData.setCode(StatusCode.OK).setResult(retResult).setMsg("获取导出数据成功");
        }catch (Exception e){
            throw new BusinessException(StatusCode.ERROR,e.getMessage());
        }
        return retData;
    }

}
