package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.VO.BusinessTypeVO;
import com.yyicbc.beans.business.VO.CompanyBaseVO;
import com.yyicbc.beans.querycondition.BusinessTypeQuestVO;
import com.yyicbc.beans.querycondition.CompanyQuestVO;

public interface BusinessTypeService {

    RetData getData(BusinessTypeQuestVO vo);

    RetData addOrUpdateData(BusinessTypeVO vo);

    RetData deleteData(BusinessTypeQuestVO vo);

    RetData getList();
}
