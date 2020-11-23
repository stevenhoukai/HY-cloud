package com.yyicbc.service;

import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.VO.CompensationVO;
import com.yyicbc.beans.querycondition.CompensationQuestVO;

import java.util.List;

public interface CompensationService {

    RetData save(List<CompensationVO> compensationVOs) throws Exception;

    RetData save(CompensationVO compensationVO)throws Exception;

    RetData delete(Long compensationId);

    RetData getDate(CompensationQuestVO questVO);

    RetData update(CompensationVO compensationVO);

    RetData doUnApprove(CompensationVO compensationVO);
}
