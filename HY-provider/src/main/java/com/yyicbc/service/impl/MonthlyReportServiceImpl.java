package com.yyicbc.service.impl;

import com.yyicbc.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.querycondition.DailyReportQueryVO;
import com.yyicbc.beans.querycondition.MonthlyReportQueryVO;
import com.yyicbc.beans.report.DailyReportVO;
import com.yyicbc.beans.report.MonthlyReportVO;
import com.yyicbc.component.pagehelper.JpaPageHelper;
import com.yyicbc.component.pagehelper.PageInfo;
import com.yyicbc.dao.DailyReportDao;
import com.yyicbc.dao.MonthlyReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/***
 * @author stv
 * 日报接口实现类，切面事务管理
 */
@Service
public class MonthlyReportServiceImpl {



    @Autowired
    MonthlyReportDao monthlyReportDao;

//    @Value("${commons.page.size}")
    private Integer Common_page_size = 500;


    public RetData all(MonthlyReportQueryVO vo) throws BusinessException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date handledate = new Date(Long.parseLong(vo.getHandleDate()));
        String yearAndMonth = format.format(handledate).substring(0,7).replaceAll("-","");
//        Date enddate = new Date(Long.parseLong(vo.getHandleEndDate()));
//        String strendtime = format.format(enddate).substring(0,10).replaceAll("-","");
//        String yearAndMonth = "202001";
        Integer page = vo.getPage();
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            List<Object[]> allFQList = monthlyReportDao.findObjectByDeductionDateFQ(yearAndMonth);
            List<MonthlyReportVO> voList=new ArrayList<MonthlyReportVO>();
            Double sumCount = new Double(0);
            Double MOPSum = new Double(0);
            Double HKDSum = new Double(0);
            Double RMBSum = new Double(0);
            Double USDSum = new Double(0);

            for(Object[] obj:allFQList){
                MonthlyReportVO reportVO=new MonthlyReportVO();
                reportVO.setAutopay_date(obj[0]==null?"":obj[0].toString());
                reportVO.setCompany_txn_type(obj[1]==null?"":obj[1].toString());
                reportVO.setCompany_dr_cr_ac(obj[2]==null?"":obj[2].toString());
                reportVO.setCompany_desc(obj[3]==null?"":obj[3].toString());
                reportVO.setTxn_ccy(obj[4]==null?"":obj[4].toString());
                reportVO.setTxn_amt(obj[5]==null?new Double(0):new Double(obj[5].toString()));
                reportVO.setRec_cnt(obj[6]==null?new Double(0):new Double(obj[6].toString()));
                voList.add(reportVO);
                sumCount += reportVO.getRec_cnt();
                if(reportVO.getTxn_ccy().equals("MOP")){
                    MOPSum += reportVO.getTxn_amt();
                }else if(reportVO.getTxn_ccy().equals("HKD")){
                    HKDSum += reportVO.getTxn_amt();
                }else if(reportVO.getTxn_ccy().equals("RMB")){
                    RMBSum += reportVO.getTxn_amt();
                }else if(reportVO.getTxn_ccy().equals("USD")){
                    USDSum += reportVO.getTxn_amt();
                }
            }
//            MonthlyReportVO countSumVO=new MonthlyReportVO();
//            countSumVO.setAutopay_date("S1");
//            countSumVO.setTxn_ccy("交易合计");
//            countSumVO.setTxn_amt(sumCount);
//            countSumVO.setRec_cnt(new Double(0));
//            voList.add(countSumVO);
//
//            MonthlyReportVO mopSumVO=new MonthlyReportVO();
//            mopSumVO.setAutopay_date("S2");
//            mopSumVO.setTxn_ccy("澳门币合计");
//            mopSumVO.setTxn_amt(MOPSum);
//            mopSumVO.setRec_cnt(new Double(0));
//            voList.add(mopSumVO);
//
//            MonthlyReportVO hkdSumVO=new MonthlyReportVO();
//            hkdSumVO.setAutopay_date("S3");
//            hkdSumVO.setTxn_ccy("港币合计");
//            hkdSumVO.setTxn_amt(HKDSum);
//            hkdSumVO.setRec_cnt(new Double(0));
//            voList.add(hkdSumVO);
//
//            MonthlyReportVO rmbSumVO=new MonthlyReportVO();
//            rmbSumVO.setAutopay_date("S4");
//            rmbSumVO.setTxn_ccy("人民币合计");
//            rmbSumVO.setTxn_amt(RMBSum);
//            rmbSumVO.setRec_cnt(new Double(0));
//            voList.add(rmbSumVO);
//
//            MonthlyReportVO usdSumVO=new MonthlyReportVO();
//            usdSumVO.setAutopay_date("S5");
//            usdSumVO.setTxn_ccy("美元合计");
//            usdSumVO.setTxn_amt(USDSum);
//            usdSumVO.setRec_cnt(new Double(0));
//            voList.add(usdSumVO);
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


    public RetData exportExcel(MonthlyReportQueryVO vo) throws BusinessException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date begindate = new Date(Long.parseLong(vo.getHandleBeginDate()));
//        String strbegintime = format.format(begindate).substring(0,10).replaceAll("-","");
//        Date enddate = new Date(Long.parseLong(vo.getHandleEndDate()));
//        String strendtime = format.format(enddate).substring(0,10).replaceAll("-","");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date handledate = new Date(Long.parseLong(vo.getHandleDate()));
        String yearAndMonth = format.format(handledate).substring(0,7).replaceAll("-","");
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            //allList为需要导出数据
            List<Object[]> allFQList = monthlyReportDao.findObjectByDeductionDateFQ(yearAndMonth);
            List<MonthlyReportVO> voList=new ArrayList<MonthlyReportVO>();
            Double sumCount = new Double(0);
            Double MOPSum = new Double(0);
            Double HKDSum = new Double(0);
            Double RMBSum = new Double(0);
            Double USDSum = new Double(0);

            for(Object[] obj:allFQList){
                MonthlyReportVO reportVO=new MonthlyReportVO();
                reportVO.setAutopay_date(obj[0]==null?"":obj[0].toString());
                reportVO.setCompany_txn_type(obj[1]==null?"":obj[1].toString());
                reportVO.setCompany_dr_cr_ac(obj[2]==null?"":obj[2].toString());
                reportVO.setCompany_desc(obj[3]==null?"":obj[3].toString());
                reportVO.setTxn_ccy(obj[4]==null?"":obj[4].toString());
                reportVO.setTxn_amt(obj[5]==null?new Double(0):new Double(obj[5].toString()));
                reportVO.setRec_cnt(obj[6]==null?new Double(0):new Double(obj[6].toString()));
                voList.add(reportVO);
                sumCount += reportVO.getRec_cnt();
                if(reportVO.getTxn_ccy().equals("MOP")){
                    MOPSum += reportVO.getTxn_amt();
                }else if(reportVO.getTxn_ccy().equals("HKD")){
                    HKDSum += reportVO.getTxn_amt();
                }else if(reportVO.getTxn_ccy().equals("RMB")){
                    RMBSum += reportVO.getTxn_amt();
                }else if(reportVO.getTxn_ccy().equals("USD")){
                    USDSum += reportVO.getTxn_amt();
                }
            }
            MonthlyReportVO countSumVO=new MonthlyReportVO();
            countSumVO.setAutopay_date("S1");
            countSumVO.setTxn_ccy("交易合计");
            countSumVO.setTxn_amt(sumCount);
            countSumVO.setRec_cnt(new Double(0));
            voList.add(countSumVO);

            MonthlyReportVO mopSumVO=new MonthlyReportVO();
            mopSumVO.setAutopay_date("S2");
            mopSumVO.setTxn_ccy("澳门币合计");
            mopSumVO.setTxn_amt(MOPSum);
            mopSumVO.setRec_cnt(new Double(0));
            voList.add(mopSumVO);

            MonthlyReportVO hkdSumVO=new MonthlyReportVO();
            hkdSumVO.setAutopay_date("S3");
            hkdSumVO.setTxn_ccy("港币合计");
            hkdSumVO.setTxn_amt(HKDSum);
            hkdSumVO.setRec_cnt(new Double(0));
            voList.add(hkdSumVO);

            MonthlyReportVO rmbSumVO=new MonthlyReportVO();
            rmbSumVO.setAutopay_date("S4");
            rmbSumVO.setTxn_ccy("人民币合计");
            rmbSumVO.setTxn_amt(RMBSum);
            rmbSumVO.setRec_cnt(new Double(0));
            voList.add(rmbSumVO);

            MonthlyReportVO usdSumVO=new MonthlyReportVO();
            usdSumVO.setAutopay_date("S5");
            usdSumVO.setTxn_ccy("美元合计");
            usdSumVO.setTxn_amt(USDSum);
            usdSumVO.setRec_cnt(new Double(0));
            voList.add(usdSumVO);

            retResult.setItem_list(voList);
            retData.setCode(StatusCode.OK).setResult(retResult).setMsg("获取导出数据成功");
        }catch (Exception e){
            throw new BusinessException(StatusCode.ERROR,e.getMessage());
        }
        return retData;
    }


//    public  List<Map<String, Object>> ListBeanToListMap(List<User> list) throws NoSuchMethodException,
//            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
//
//        for (Object ob : list) {
//            listmap.add(BeanMapUtils.beanToMap(ob));
//        }
//        return listmap;
//    }



}
