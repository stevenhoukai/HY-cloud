package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.imports.VO.PdfTemplateVO;
import com.yyicbc.beans.querycondition.PdfTemplateQuestVO;

public interface PdfTemplateService {

    RetData getData(PdfTemplateQuestVO vo);

    RetData addOrUpdateData(PdfTemplateVO vo);

    RetData deleteData(PdfTemplateQuestVO vo);
}
