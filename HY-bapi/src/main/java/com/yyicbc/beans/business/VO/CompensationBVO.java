package com.yyicbc.beans.business.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompensationBVO {
    private static final long serialVersionUID = 32345L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long compensationId;

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
