package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.imports.VO.ExcelTemplateVO;
import com.yyicbc.beans.querycondition.ExcelTemplateQuestVO;

public interface ExcelTemplateService {

    RetData getData(ExcelTemplateQuestVO vo);

    RetData addOrUpdateData(ExcelTemplateVO vo);

    RetData deleteData(ExcelTemplateQuestVO vo);
}
