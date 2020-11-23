package com.yyicbc.jobservice;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.VO.CompensationVO;
import com.yyicbc.beans.business.VO.SendHCMUpdateVO;
import com.yyicbc.beans.querycondition.CompensationQuestVO;

import java.util.List;

public interface CompensationService {

    RetData update(List<CompensationVO> compensationVOs);

    RetData updateSendStatus(List<Long> compensationIds);

    List<SendHCMUpdateVO> getAll(CompensationQuestVO questVO);
}
