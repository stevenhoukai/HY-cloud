package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.VO.CompanyBaseVO;
import com.yyicbc.beans.querycondition.CompanyQuestVO;

public interface CompanyFileService {

    RetData getData(CompanyQuestVO vo);

    RetData addOrUpdateData(CompanyBaseVO vo);

    RetData deleteData(CompanyQuestVO vo);

    RetData getTemplate();

    RetData findAll();
}
