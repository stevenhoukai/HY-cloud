package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.business.VO.DealSymbolVO;
import com.yyicbc.beans.querycondition.CusetomerAgreementQueryVO;
import com.yyicbc.beans.querycondition.DealSymbolQuestVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DealSymbolService
{
    RetData getData(DealSymbolQuestVO vo);

    RetData add(DealSymbolVO dealSymbolVO);

    RetData update(DealSymbolVO dealSymbolVO);

    RetData delete(DealSymbolQuestVO vo);


    /**功能描述:
     * @param 返回所有处理标志
    * @return: {@link java.util.List}
    * @Author: vic
    * @Date: 2020/3/27 17:29
    */
    List findAll() ;
        
}
