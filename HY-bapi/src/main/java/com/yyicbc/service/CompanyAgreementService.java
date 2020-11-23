package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.PO.CompanyAgreementPO;
import com.yyicbc.beans.business.VO.CompanyAgreementVO;
import com.yyicbc.beans.querycondition.CompanyQuestVO;

import java.util.Date;

public interface CompanyAgreementService {

    RetData getData(CompanyQuestVO vo);

    RetData getCompanyAccount(CompanyQuestVO vo);

    RetData addOrUpdateData(CompanyAgreementVO vo);

    RetData deleteData(CompanyQuestVO vo);

    RetData searchAccount(CompanyQuestVO vo);

    RetData updateAgreementStatusAndDateById(Date date, String agreementStatus, Long Id);


    /**
     * add by fmm
     * 根據公司代碼獲取協議檔案
     * @param companyCode
     * @return
     */
    CompanyAgreementPO getCompanyAgreementPOByCompanyCode(String companyCode);
}
