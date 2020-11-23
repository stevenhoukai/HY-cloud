package com.yyicbc.beans.querycondition;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class UserImportQuestVO extends BaseQuestVO {
    private String companyEncode;//公司编码(唯一)
    private String companyCode;
    private String companyAccount;
    private String customerAccount;
    private String errMessage;
    private String cAccount;
    private String currencyType;
    private String traCurr;
    private String importDate;
    private String fileName;
    private String amount;
    private String importFileName;
    private MultipartFile file =null;
    private int fileType;
    private int fileStatus;

    private Long flowId;
    private String flowNumber;
    private String runNumber;
    private Long makerId;
    private Long checkerId;

    private String deductionDate;
    private String tranDate;
    private String valueDat;
    private String sendDate;
    private String seqNo;
    private String updateType;
    private String mediumid;
    private String fovaUpdateStatus;
    private String importDateBegin;
    private String importDateEnd;
}
