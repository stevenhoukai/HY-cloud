package com.yyicbc.beans.querycondition;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CompanyQuestVO extends BaseQuestVO {
    private String companyName;
    private String companyEncode;//公司编码(不唯一)
    private String companyCode;//公司代码(唯一)
    private String companyAccount;//公司账号
    private String beneficiaryId;
    private String beneficiaryName;
    private String billNumber;// 账单编号
    private String fowaAccount;// fowa 账号
    private String excelTemplateType;
    private String exportExcelTemplateType;
    private String txtTemplateType;
    private String exportTxtTemplateType;

    private String signDateBegin;
    private String signDateEnd;
    private String generateDateBegin;
    private String generateDateEnd;
    private String reportDateBegin;
    private String reportDateEnd;
    private String generateDate;

    private String referenceTransaction;
    private String mainAgreementNumber;
    private String customerIndex;
    private String accountName;
    private String billNumberRestriction;
}
