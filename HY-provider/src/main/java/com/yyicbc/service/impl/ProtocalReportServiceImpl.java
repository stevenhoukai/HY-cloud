package com.yyicbc.service.impl;

import com.yyicbc.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.querycondition.DailyReportQueryVO;
import com.yyicbc.beans.querycondition.LogQueryVO;
import com.yyicbc.beans.querycondition.ProtocalReportQueryVO;
import com.yyicbc.beans.report.DailyReportVO;
import com.yyicbc.beans.report.ProtocalReportVO;
import com.yyicbc.beans.report.SubProtocalReportVO;
import com.yyicbc.component.pagehelper.JpaPageHelper;
import com.yyicbc.component.pagehelper.PageInfo;
import com.yyicbc.dao.DailyReportDao;
import com.yyicbc.dao.ProtocalReportDao;
import com.yyicbc.dao.SubProtocalReportDao;
import com.yyicbc.dao.SysLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/***
 * @author stv
 * 协议报表接口实现类，切面事务管理
 */
@Service
public class ProtocalReportServiceImpl {


    @Autowired
    ProtocalReportDao protocalReportDao;

    @Autowired
    SubProtocalReportDao subProtocalReportDao;

//    @Value("${commons.page.size}")
    private Integer Common_page_size = 300;


    public RetData all(ProtocalReportQueryVO vo) throws BusinessException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date begindate = new Date(Long.parseLong(vo.getHandleBeginDate()));
        String strbegintime = format.format(begindate).substring(0,10);
        Date enddate = new Date(Long.parseLong(vo.getHandleEndDate()));
        String strendtime = format.format(enddate).substring(0,10);
        Integer page = vo.getPage();
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            List<Object[]> allList = protocalReportDao.findObjectsByEffectDate(strbegintime,strendtime);
            List<ProtocalReportVO> voList=new ArrayList<ProtocalReportVO>();
            for(Object[] obj:allList){
                ProtocalReportVO protocalReportVO=new ProtocalReportVO();
                protocalReportVO.setCompanyCode(obj[0]==null?"":obj[0].toString());
                protocalReportVO.setCompanyName(obj[1]==null?"":obj[1].toString());
                protocalReportVO.setCompanyAccount(obj[2]==null?"":obj[2].toString());
                protocalReportVO.setMainNumber(obj[3]==null?"":obj[3].toString());
                protocalReportVO.setEffectDate(obj[4]==null?"":obj[4].toString());
                protocalReportVO.setBusinessType(obj[5]==null?"":obj[5].toString());
                protocalReportVO.setBusinessTypename(obj[6]==null?"":obj[6].toString());
                protocalReportVO.setCcy(obj[7]==null?"":obj[7].toString());
                protocalReportVO.setFlag(obj[8]==null?"":obj[8].toString());
                protocalReportVO.setStatus(obj[9]==null?"":obj[9].toString());
                voList.add(protocalReportVO);
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

    public RetData allSub(ProtocalReportQueryVO vo) throws BusinessException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date begindate = new Date(Long.parseLong(vo.getHandleBeginDate()));
        String strbegintime = format.format(begindate).substring(0,10)+" 00:00:00";
        Date enddate = new Date(Long.parseLong(vo.getHandleEndDate()));
        String strendtime = format.format(enddate).substring(0,10)+" 23:59:59";
        Integer page = vo.getPage();
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            List<CusetomerAgreementPO> allList = subProtocalReportDao.findObjectsByReportDate(strbegintime,strendtime);
            List<SubProtocalReportVO> voList=new ArrayList<SubProtocalReportVO>();
            for(CusetomerAgreementPO po : allList){
                SubProtocalReportVO subprotocalReportVO=new SubProtocalReportVO();

                subprotocalReportVO.setCompany_code(po.getCompanyCode());
                subprotocalReportVO.setTrade_code(po.getTradeCode());
                subprotocalReportVO.setAccount_name(po.getAccountName());
                subprotocalReportVO.setFowa_account(po.getFowaAccount());
                subprotocalReportVO.setSub_agreement_number(po.getSubAgreementNumber());
                subprotocalReportVO.setMain_agreement_number(po.getMainAgreementNumber());
                subprotocalReportVO.setCcy(po.getCcy());
                subprotocalReportVO.setCustomer_status(po.getCustomerStatus());
                subprotocalReportVO.setGenerate_date(po.getGenerateDate()+"");
                subprotocalReportVO.setModify_time(po.getModifyTime()+"");
                subprotocalReportVO.setCancel_date(po.getCancelDate()+"");
                subprotocalReportVO.setReport_time(po.getReportTime()+"");
                subprotocalReportVO.setBeneficiary_id(po.getBeneficiaryId());
                subprotocalReportVO.setReference_code(po.getReferenceCode());
                subprotocalReportVO.setReference_transaction(po.getReferenceTransaction());
                subprotocalReportVO.setBill_number(po.getBillNumber());
                subprotocalReportVO.setComments(po.getComments());
                subprotocalReportVO.setProduct_index(po.getProductIndex());
                subprotocalReportVO.setStop_amount_per(po.getStopAmountPer());
                voList.add(subprotocalReportVO);
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


    public RetData exportExcel(ProtocalReportQueryVO vo) throws BusinessException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date begindate = new Date(Long.parseLong(vo.getHandleBeginDate()));
        String strbegintime = format.format(begindate).substring(0,10);
        Date enddate = new Date(Long.parseLong(vo.getHandleEndDate()));
        String strendtime = format.format(enddate).substring(0,10);
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            //allList为需要导出数据
            List<Object[]> allList = protocalReportDao.findObjectsByEffectDate(strbegintime,strendtime);
            List<ProtocalReportVO> voList=new ArrayList<ProtocalReportVO>();
            for(Object[] obj:allList){
                ProtocalReportVO protocalReportVO=new ProtocalReportVO();
                protocalReportVO.setCompanyCode(obj[0]==null?"":obj[0].toString());
                protocalReportVO.setCompanyName(obj[1]==null?"":obj[1].toString());
                protocalReportVO.setCompanyAccount(obj[2]==null?"":obj[2].toString());
                protocalReportVO.setMainNumber(obj[3]==null?"":obj[3].toString());
                protocalReportVO.setEffectDate(obj[4]==null?"":obj[4].toString());
                protocalReportVO.setBusinessType(obj[5]==null?"":obj[5].toString());
                protocalReportVO.setBusinessTypename(obj[6]==null?"":obj[6].toString());
                protocalReportVO.setCcy(obj[7]==null?"":obj[7].toString());
                protocalReportVO.setFlag(obj[8]==null?"":obj[8].toString());
                protocalReportVO.setStatus(obj[9]==null?"":obj[9].toString());
                voList.add(protocalReportVO);
            }
            retResult.setItem_list(voList);
            retData.setCode(StatusCode.OK).setResult(retResult).setMsg("获取导出数据成功");
        }catch (Exception e){
            throw new BusinessException(StatusCode.ERROR,e.getMessage());
        }
        return retData;
    }


    public RetData exportSubExcel(ProtocalReportQueryVO vo) throws BusinessException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date begindate = new Date(Long.parseLong(vo.getHandleBeginDate()));
        String strbegintime = format.format(begindate).substring(0,10)+" 00:00:00";
        Date enddate = new Date(Long.parseLong(vo.getHandleEndDate()));
        String strendtime = format.format(enddate).substring(0,10)+ "23:59:59";
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            //allList为需要导出数据
            List<CusetomerAgreementPO> allList = subProtocalReportDao.findObjectsByReportDate(strbegintime,strendtime);
            List<SubProtocalReportVO> voList=new ArrayList<SubProtocalReportVO>();
            for(CusetomerAgreementPO po:allList){
                SubProtocalReportVO subprotocalReportVO=new SubProtocalReportVO();
                subprotocalReportVO.setCompany_code(po.getCompanyCode());
                subprotocalReportVO.setTrade_code(po.getTradeCode());
                subprotocalReportVO.setAccount_name(po.getAccountName());
                subprotocalReportVO.setFowa_account(po.getFowaAccount());
                subprotocalReportVO.setSub_agreement_number(po.getSubAgreementNumber());
                subprotocalReportVO.setMain_agreement_number(po.getMainAgreementNumber());
                subprotocalReportVO.setCcy(po.getCcy());
                subprotocalReportVO.setCustomer_status(po.getCustomerStatus());
                subprotocalReportVO.setGenerate_date(po.getGenerateDate()+"");
                subprotocalReportVO.setModify_time(po.getModifyTime()+"");
                subprotocalReportVO.setCancel_date(po.getCancelDate()+"");
                subprotocalReportVO.setReport_time(po.getReportTime()+"");
                subprotocalReportVO.setBeneficiary_id(po.getBeneficiaryId());
                subprotocalReportVO.setReference_code(po.getReferenceCode());
                subprotocalReportVO.setReference_transaction(po.getReferenceTransaction());
                subprotocalReportVO.setBill_number(po.getBillNumber());
                subprotocalReportVO.setComments(po.getComments());
                subprotocalReportVO.setProduct_index(po.getProductIndex());
                subprotocalReportVO.setStop_amount_per(po.getStopAmountPer());
                voList.add(subprotocalReportVO);
            }
            retResult.setItem_list(voList);
            retData.setCode(StatusCode.OK).setResult(retResult).setMsg("获取导出数据成功");
        }catch (Exception e){
            throw new BusinessException(StatusCode.ERROR,e.getMessage());
        }
        return retData;
    }
}
