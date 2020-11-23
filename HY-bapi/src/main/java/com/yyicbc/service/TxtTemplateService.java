package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.imports.VO.TxtTemplateVO;
import com.yyicbc.beans.querycondition.TxtTemplateQuestVO;

public interface TxtTemplateService {

    RetData getData(TxtTemplateQuestVO vo);

    RetData addOrUpdateData(TxtTemplateVO vo);

    RetData deleteData(TxtTemplateQuestVO vo);
}
