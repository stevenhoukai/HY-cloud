package com.yyicbc.utils.freemarker.pdf.aia;

import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.freemarker.pdf.manulife.ManulifePdf;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vic fu
 * AIAHKD
 */
@Component
public class AiaPdf extends ManulifePdf {

    @Override
    protected List exportTxtList(List<FovaUpdateDataPO> detailData) {
        List fileList = new ArrayList<File>();
        setAmountUnit(false);
        //先進行匯總
        handleTotalMap(detailData);
        //失败记录
        File badFile = exportAiaTxt(filterData(detailData, TYPE_NEW), "ICBCMRJ1-081519.TXT");

        addFileList(fileList, badFile);

        return fileList;
    }

    /**
     * 功能描述: 1、失敗返回的txt格式
     * Detail
     * 第一列：失敗原因（01代表FOVA系統返回代碼為001；03代表FOVA系統返回代碼為003；04代表FOVA系統返回代碼為004；05代表FOVA系統返回代碼為005；其他的失敗原因則為“99”）+合同號碼
     * 第二列：固定0+金額
     *
     * @param detailData
     * @param fileName
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/2/26 18:07
     */
    private File exportAiaTxt(List<FovaUpdateDataPO> detailData, String fileName) {

        if (CollectionUtils.isEmpty(detailData)) {
            return null;
        }
        StringBuffer txtBuffer = new StringBuffer();
        //Detail
        detailData.forEach(detail -> {
            String status = "99";
            if (StringUtils.isNotEmpty(detail.getErrNo())) {
                int length = detail.getErrNo().length();
                String errNo = detail.getErrNo().substring(length - 2, length);
                if ("01".equals(status) || "02".equals(status) || "03".equals(status) &&
                        "04".equals(status) || "05".equals(status)) {
                    status = errNo;
                }
            }
            txtBuffer.append(status);
            txtBuffer.append(detail.getCustomerContract());
            txtBuffer.append("  ");
            txtBuffer.append(YyStringUtils.addZero(detail.getAmount(), 13));
            txtBuffer.append("\n");
        });
        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + fileName);
    }


    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) {
        return null;
    }

    @Override
    protected List exportPdfList(List<FovaUpdateDataPO> detailData) {
       /* List fileList = new ArrayList<File>();
        //先進行匯總
        setAmountUnit(true);
        handleTotalMap(detailData);
        //失败
        File acceptFile = exportPdfFile(filterData(detailData, TYPE_NEW), getTemplateFile(), getPath() + getTemplateFile());
        if (acceptFile != null) {
            fileList.add(acceptFile);
        }
        return fileList;*/
        return null;
    }

    @Override
    protected String getTemplateFile() {
        return "AIAHKD";
    }

}
