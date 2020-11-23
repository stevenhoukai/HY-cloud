package com.yyicbc.utils;

import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.querycondition.CompanyQuestVO;
import com.yyicbc.utils.freemarker.customer.FssExport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @ClassNameExportNewApplyDownLoad
 * @Description
 * @Author vic
 * @Date2020/3/25 15:45
 * @Version V1.0
 **/

@Component
public class ExportNewApplyDownLoad {

    @Resource
    FssExport fssExport;

    public  File exportFile(List<CusetomerAgreementPO> list, CompanyQuestVO request) throws Exception {

        return fssExport.exportFile(list,request);
    }


}
