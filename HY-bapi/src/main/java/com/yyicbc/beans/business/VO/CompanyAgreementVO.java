package com.yyicbc.beans.business.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
public class CompanyAgreementVO implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String companyEncode;
    private String companyCode;
    private String companyName;
    private String companyAccount;
    private String oldAccount;

    private String searchCondition;
    private String ccy;
    private String businessTypeCode;
    private String mainAgreementNumber;
    private String agreementStatus;

    private String payLimit;
    private String signDriect;
    private String paySign;
    private String productType;
    private String productIndex;
    private String paymethod;
    private String borrowSign;
    private String productTypeShort;
    private String chargeSign;
    private String chargeType;
    private String chargeMethod;
    private String chargeByNumberStandard;
    private String agreementChargeSign;
    private String maxAmount;
    private String minAmount;
    private String regularQuotaSign;
    private String tradeCode;
    private String inAccountPosition;
    private String companySign;
    private String agreementType;
    private String agentCertificatesType;
    private String comments;
    private String abstractContent;
    private String signDate;
    private String effectDate;
    private String endDate;
    private String billIndex;
    private String accountIndex;
    private String accountName;
    private String accountNameEng;
    private String customerIndex;
    private String orderIndex;
    private String stopAmountPer;
    private String stopAmountMonth;
    private String scalepc;
    private String type;

}
