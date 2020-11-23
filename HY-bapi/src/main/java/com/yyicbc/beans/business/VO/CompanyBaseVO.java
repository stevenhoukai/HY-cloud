package com.yyicbc.beans.business.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBaseVO implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String companyEncode;
    private String companyName;
    private String companyDescription;
    private String drCrAccount;
    private String companyPhone;
    private String personalPhone;
    private String fax;
    private String companyStatus;
    private String companyContact;
    private String personalContact;
    private String fileType;
    private String excelTemplateType;
    private String txtTemplateType;
    private String exportFileType;
    private String exportExcelTemplateType;
    private String exportTxtTemplateType;
    private String exportPdfTemplateType;
    private String address1;
    private String accountName;
    private String accountNameEng;
    private String Email1;
    private String Email2;
    private List<CompanyAccountVO> accountList;
    private String exportTemplateMinunit;
    private Integer isRequiredBillNumber;
}
