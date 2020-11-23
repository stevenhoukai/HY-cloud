package com.yyicbc.utils.freemarker.pdf.massmutu;

import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.freemarker.pdf.AbstractPdfExport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vic fu
 */
@Component
public class MassmutuExport extends AbstractPdfExport {


    @Override
    protected List exportPdfList(List<FovaUpdateDataPO> detailData) {
        return null;
    }

    @Override
    protected List exportTxtList(List<FovaUpdateDataPO> detailData) {
        List fileList = new ArrayList<File>();
        setAmountUnit(false);
        //先進行匯總
        handleTotalMap(detailData);
        //成功
        File accpetFile = exportAcceptTxt(filterData(detailData,TYPE_PAY));
        //失败
        File rejectFile = exportRejectTxt(filterData(detailData,TYPE_BAD));

        addFileList(fileList, accpetFile);
        addFileList(fileList, rejectFile);
        return fileList;
    }

    /**
     * 1、	YF_LIFE_INS-0902.ACCEPT
     * 客戶賬號+客戶名稱+HKD（幣種）+016(借貸方向，016貸方，516借方)+借方金額+貸方金額+合同號碼
     * 最後一行：公司賬號+公司名稱+幣種+借貸方向+總金額+0
     *
     * @param detailData
     * @return
     */
    private File exportAcceptTxt(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        //Detail
        int maxNameLength = getMaxNameLength(detailData);
        if(getCompanyName().length()>maxNameLength){
            maxNameLength = getCompanyName().length();
        }
        int finalMaxNameLength = maxNameLength;
        detailData.forEach(detail -> {
            txtBuffer.append(detail.getCustomerAccount());
            txtBuffer.append(YyStringUtils.addSpaceByLeft(detail.getCustomerName(), finalMaxNameLength));
            txtBuffer.append(detail.getCurrType() + "016");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("0", 14));
            txtBuffer.append(YyStringUtils.addSpaceByLeft(getAmountByUnit(detail.getAmount()), 14));
            txtBuffer.append(detail.getCustomerContract());
            txtBuffer.append("\r\n");
        });
        //最后一行
        txtBuffer.append(getCompanyAccount());
        txtBuffer.append(YyStringUtils.addSpaceByLeft(getCompanyName(), finalMaxNameLength));
        txtBuffer.append(detailData.get(0).getCurrType() + "056");
        txtBuffer.append(YyStringUtils.addSpaceByLeft(getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT), 14));
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0", 14));

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + "YF_LIFE_INS-" + transDateFileName + ".ACCEPT");
    }


    /**
     *2、	YF_LIFE_INS-0902.REJECT
     * 客戶賬號+客戶名稱+HKD（幣種）+016(借貸方向，016貸方，516借方)+借方金額+貸方金額+失敗代號（J）+合同號碼
     *
     * @param detailData
     * @return
     */
    private File exportRejectTxt(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        int maxNameLength = getMaxNameLength(detailData);
        //Detail
        detailData.forEach(detail -> {
            txtBuffer.append(detail.getCustomerAccount());
            txtBuffer.append(YyStringUtils.addSpaceByLeft(detail.getCustomerName(), maxNameLength));
            txtBuffer.append(detail.getCurrType() + "016");
            txtBuffer.append(YyStringUtils.addSpaceByLeft("0", 14));
            txtBuffer.append(YyStringUtils.addSpaceByLeft(getAmountByUnit(detail.getAmount()), 14));
            txtBuffer.append(YyStringUtils.addSpaceByLeft("J", 10));
            txtBuffer.append(YyStringUtils.addSpaceByLeft(detail.getCustomerContract(), 33));
            txtBuffer.append("\r\n");
        });

        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + "YF_LIFE_INS-" + transDateFileName + ".REJECT");
    }

    /**
     * 获取名字最大字符串长度
     *
     * @param detailData
     * @return
     */
    private int getMaxNameLength(List<FovaUpdateDataPO> detailData) {
        int length = detailData.stream().filter(detail -> StringUtils.isNotBlank(detail.getCustomerName())).mapToInt(detail -> detail.getCustomerName().length()).filter(detail -> detail >= 0).max().orElse(0);
        return length;
    }

    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) {
        return null;
    }
}
