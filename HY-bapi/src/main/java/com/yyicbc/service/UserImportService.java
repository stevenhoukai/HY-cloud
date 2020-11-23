package com.yyicbc.service;

import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.DTO.ExcelHeaderDetailDTO;
import com.yyicbc.beans.business.DTO.TxtHeaderDetailDTO;
import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.business.PO.UserImportPO;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.querycondition.UserImportQuestVO;

import java.util.List;

public interface UserImportService {

    RetData getData(UserImportQuestVO vo);

    RetData delete(UserImportQuestVO vo);

    RetData review(UserImportQuestVO vo);

    RetData abandon(UserImportQuestVO vo);

    RetData getDetailList(UserImportQuestVO vo);

    RetData updateDetail(UserImportQuestVO vo);

    RetData getCompanyCode(UserImportQuestVO vo);

    RetData getHeader(UserImportQuestVO vo);

    RetData importFile(UserImportQuestVO vo);

    CompanyBasePO getCompanyTemplate(UserImportQuestVO vo);

    byte[] downloadOrigionFile(UserImportQuestVO vo);

    TxtHeaderDetailDTO getTxtDataFullHead(UserImportQuestVO vo);

    ExcelHeaderDetailDTO getExcelDataFullHead(UserImportQuestVO vo);

    List<FovaUpdateDataPO> getDataByflowId(Long flowId);

    UserImportPO findUserImportPOById(Long id);


    /**功能描述:DSF 补号
     * @param vo
    * @return: {@link RetData}
    * @Author: vic
    * @Date: 2020/3/26 10:46
    */
    void addDsfAccno(UserImportQuestVO vo)throws BusinessException;
}
