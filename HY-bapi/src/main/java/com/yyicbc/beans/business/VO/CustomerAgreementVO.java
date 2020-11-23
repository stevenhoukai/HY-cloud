package com.yyicbc.beans.business.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
public class CustomerAgreementVO implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String companyCode;
    private String mainAgreementNumber;
    private String billNumber;
    private String fowaAccount;
    private String beneficiaryId;
    private String beneficiaryName;
    private String subAgreementNumber;
    private String customerStatus;
    private String address1;
    private String reportTime = null;
    private String modifyTime =null;
    private String cancelDate = null;
    private String generateDate;
    private String signDriect;
    private String startAccountName;
    private String paySign;
    private String accountName;
    private String accountNameEng;
    private String accountIndex;
    private String productType;
    private String productIndex;
    private String paymethod;
    private String borrowSign;
    private String productTypeShort;
    private String ccy;
    private String stopCCY;
    private String stopAmountPer;
    private String stopAmountMonth;
    private String chargeSign;
    private String customerIndex;
    private String chargeMethod;
    private String regularQuotaSign;
    private String tradeCode;
    private String inAccountPosition;
    private String chargeByNumberStandard;
    private String companySign;
    private String agreementType;
    private String comments;
    private String abstractContent;
    private String oldAccount;
    private String referenceCode;
    private String referenceTransaction;
    private String referenceTransactionEnd;
    private String referenceTransactionNet;
    private String referenceTransactionTxt;
    private String scalepc;
    private String maxAmount;
    private String minAmount;
    private String orderIndex;
    private String type;
}
