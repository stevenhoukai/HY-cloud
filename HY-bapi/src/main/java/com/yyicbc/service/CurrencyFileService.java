package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.querycondition.CurrencyQueryVO;
import com.yyicbc.beans.business.VO.CurrencyBaseVO;

import java.util.List;

public interface CurrencyFileService {

    RetData getData(CurrencyQueryVO vo);

    RetData addOrUpdateData(CurrencyBaseVO vo);

    RetData deleteData(CurrencyBaseVO vo);

    RetData findAll();

    RetData findByStatus(int status);

    List findAllList();

}
