package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.business.VO.CustomerAgreementVO;
import com.yyicbc.beans.querycondition.CompanyQuestVO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CustomerAgreementService {

    RetData getData(CompanyQuestVO vo);

    RetData addOrUpdateData(CustomerAgreementVO vo);

    RetData updateStatus(CustomerAgreementVO vo);

    RetData deleteData(CompanyQuestVO vo);

    RetData generateSubCode(CompanyQuestVO vo);

    RetData getCompanyCode(CompanyQuestVO vo);

    RetData getCompanyEncode(CompanyQuestVO vo);

    RetData searchAccount(CompanyQuestVO vo);

    RetData updateCustomerStatusAndDateById(Date date, String customerStatus, Long Id);

    /**
     * add by fmm
     * 根据账号获取公司客户档案信息
     * @param fowaAccountList
     * @return
     */
    List<CusetomerAgreementPO> findByfowaAccount(List<String> fowaAccountList);


    /**功能描述: 获取该公司下所有客户
     * @param companyCode
    * @return: {@link Map}
    * @Author: vic
    * @Date: 2020/3/11 16:30
    */
    HashMap<String,CusetomerAgreementPO> findCusetomerPOMapByCompanyCode(String companyCode);

    /**
     * 生成3 种参考交易号码
     *
     * @param companyCode
     * @param referenceTransaction
     * @return
     */
    String generateReferenceTransactionCode(String companyCode, String referenceTransaction);

    /**
     * 生成公司报表日期
     *
     * @param vo
     * @return
     */
    RetData generateCompanyReportDate(CompanyQuestVO vo);


    /**功能描述: 根据日期获取
     * 1、根据查询日期，将最近一周的“新增服务和取消服务”按社保需要的格式导出txt格式和PDF格式。
     * 2、如果查询当周 星期五，则取上周五到当周周五的数据；如果“查询日期”属于当周星期一到星期四的，则取上上周五到上周四的数据。
     * @param vo
    * @return: {@link List< CusetomerAgreementPO>}
    * @Author: vic
    * @Date: 2020/3/24 15:25
    */
    List<CusetomerAgreementPO>  getNewApplyDataByDate(CompanyQuestVO vo);
}
