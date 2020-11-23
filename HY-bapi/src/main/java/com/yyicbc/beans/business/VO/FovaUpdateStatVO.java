package com.yyicbc.beans.business.VO;

import com.yyicbc.beans.business.PO.CompensationPO;
import com.yyicbc.beans.business.PO.UserImportPO;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FovaUpdateStatVO {
    private String depZone;
    private String updateDate;//上传日期
    private String valueDate;//起息日期
    private String tranDate;//入账日期
    private String binFileName;
    private long totalCounter;
    private long totalAmount;
    private long binFileLength;
    private int minCurrency = 1;
    private String checkFileName;
    private List<FovaUpdateDataPO> fovaUpdateDataPOS;

    public FovaUpdateStatVO(UserImportPO po){
        this.updateDate = po.getSendDate();
        this.tranDate = po.getTranDate();
        this.valueDate = po.getValueDat();
    }

    public FovaUpdateStatVO(CompensationPO po){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        this.updateDate = df.format(po.getUploadDate());
        this.tranDate = df.format(po.getBookedDate());
        this.valueDate = df.format(po.getInterestDate());
    }
}
