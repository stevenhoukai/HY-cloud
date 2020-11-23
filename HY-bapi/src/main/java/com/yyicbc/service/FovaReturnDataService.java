package com.yyicbc.service;

import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.PO.UserImportPO;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.querycondition.UserImportQuestVO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

public interface FovaReturnDataService {

    void updateFovaData(Map paramMap) throws BusinessException;

    void saveReturnFovaData() throws BusinessException;

    RetData updateFovaDataTest(UserImportPO request);

    RetData updateFovaDataTest(Long compensationId);

    RetData fovaReturnFile(UserImportQuestVO request);


    /**
     * add by fmm
     * 根据FovaData Srcid更新发放薪资子表状态
     * @param returnList
     * @throws Exception
     */
    void updateComBByFovaSrcData(List<FovaUpdateDataPO> returnList) throws Exception;

}
