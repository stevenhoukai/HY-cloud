package com.yyicbc.beans.business.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserImportVO implements Serializable {

    private static final long serialVersionUID = -6329611685248522031L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String companyEncode;

    private String companyCode;

    private String mainAgreementNumber;

    private String subAgreementNumber;

    private String importFileName;

    private String fileStatus;

    private String totalAmount;

    private String totalCount;

    private String uploadFileName;

    private String tranDate;

    private String valueDat;

    private String deductionDate;

    private String payAccount;

    private String flowNumber;

    private String runNumber;

    private String sendDate;

    private String errorContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long makerId;

    private Long checkerId;

    private String maker;

    private String checker;

    private Date createTime;

    private String ccy;

    private String businessType;

    private String productIndex;
}