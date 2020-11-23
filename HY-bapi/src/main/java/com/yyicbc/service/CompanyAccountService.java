package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.VO.CompanyAccountVO;

public interface CompanyAccountService {

    RetData getCompanyAccountsByBusinessTypeCodeAndCcyCode(CompanyAccountVO vo);
}
