package com.yyicbc.beans.business.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompensationVO{
    private static final long serialVersionUID = 32345L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private Integer serialNumber;

    private String companyNo;

    private String companyName;

    private String runBatch;

    private Date uploadDate;

    private Integer totalNumber;

    private Double totalAmount;

    private Date deductDate;

    private Date bookedDate;

    private Integer batchStatus;

    private String note;

    private String guid;

    private String pk_payroll;

    private Integer sendStatus;

    private String companyAccountNo;

    private Date interestDate;

    private String distributeTimes;

    private String reserveField1;

    private String reserveField2;

    private String reserveField3;

    private String reserveField4;

    private String reserveField5;

    private String vdef1;

    private String vdef2;

    private String vdef3;

    private String vdef4;

    private String vdef5;

    private List<CompensationBVO> CompensationBVOs;
}
