package com.yyicbc.beans.business.VO;

import com.yyicbc.beans.business.PO.CompensationBPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
    public class SendHCMUpdateVO implements  Serializable{

    private static final long serialVersionUID = -6420197415637503877L;

    private String compensationId;

    private String pk_payroll;

    private Integer batchStatus;

    private List<String> pk_wa_datas;

    private List<SendHCMCompensationBVO> compensationBs;
}
