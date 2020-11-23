package com.yyicbc.beans.querycondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CompensationQuestVO extends BaseQuestVO{

    private String companyNo;

    private String companyName;

    private String deductDate;

    private Integer batchStatus;

    private Integer sendStatus;

    private String deductDateBegin;

    private String deductDateEnd;
}