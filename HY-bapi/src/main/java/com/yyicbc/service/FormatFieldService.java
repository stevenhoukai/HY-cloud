package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.imports.VO.FormatFieldVO;
import com.yyicbc.beans.querycondition.FormatFieldQuestVO;

public interface FormatFieldService {

    RetData getData(FormatFieldQuestVO vo);

    RetData addOrUpdateData(FormatFieldVO vo);

    RetData deleteData(FormatFieldQuestVO vo);

    RetData getAllFormatField();
}
