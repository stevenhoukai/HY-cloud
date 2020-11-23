package com.yyicbc.utils.freemarker.pdf.sib;

import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.enums.FovaPayStatus;
import com.yyicbc.beans.enums.ServiceStatusEnums;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.freemarker.pdf.AbstractPdfExport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @ClassNameNewExport
 * @Description 社保新
 * @Author vic
 * @Date2020/2/26 17:54
 * @Version V1.0
 **/
@Component
public class SibNewExport extends SibOldExport {

    @Override
    protected boolean isNew() {
        return true;
    }
}
