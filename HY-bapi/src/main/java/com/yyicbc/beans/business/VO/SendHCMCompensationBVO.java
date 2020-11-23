package com.yyicbc.beans.business.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendHCMCompensationBVO {
    private static final long serialVersionUID = 32345L;

    private String id;

    private String compensationId;

    private String personnelCode;

    private String accountName;

    private String accountNo;

    private String currencyCode;

    private Double amountResult;

    private Integer succeedStatus;

    private String note;

    private String errorMessage;

    private String pk_wa_data;

    private String reserveField1;

    private String reserveField2;

    private String reserveField3;

    private String reserveField4;

    private String reserveField5;
}
