package com.yyicbc.utils.freemarker.pdf.esp;

import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.utils.freemarker.pdf.AbstractPdfExport;
import com.yyicbc.utils.freemarker.pdf.PdfExportUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

/**
 *@author vic fu
 */
@Component
public class EspPdf extends AbstractPdfExport {


    @Override
    protected List exportPdfList(List<FovaUpdateDataPO> detailData) {


            List fileList = new ArrayList<File>();
            //先進行匯總
            setAmountUnit(true);
            handleTotalMap(detailData);
            //导出accept
            List acceptList = filterData(detailData,TYPE_PAY);

            File acceptFile = exportPdfFile(acceptList,"ESP",getPath()+"ACCEPT");
            if(acceptFile != null){
                fileList.add(acceptFile);
            }
            //導出Reject
            List rejectList = filterData(detailData,TYPE_BAD);
            File rejectFile = exportPdfFile(rejectList,"ESP",getPath()+"REJECT");
            if(rejectFile != null){
                fileList.add(rejectFile);
            }
            return fileList;
    }

    @Override
    protected List exportTxtList(List<FovaUpdateDataPO> detailData) {
        return null;
    }

    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) {
        return null;
    }

    /**
     * 根據交易數量區分總交易數量和金額
     * @param transList
     */
    protected void handleTotalMapByTrans(List<FovaUpdateDataPO> transList){
        //總交易記錄
        int record = 0;
        //總交易金額
        Long  amount = 0L;

        for (FovaUpdateDataPO po:transList ) {
            ++record;
            amount += Long.parseLong(po.getAmount());
        }
        totalMap.put(TOTAL_KEY_RECORD,record);
        totalMap.put(TOTAL_KEY_AMOUNTDUE,getAmountByUnit(amount));

    }

    /**
     *
     * @param detailData
     * @return
     */
    private File exportPdfFile(List<FovaUpdateDataPO> detailData,String templateFile,String targetPdfFile){

        if(CollectionUtils.isEmpty(detailData)){
            return null;
        }
        //統計區分Accept和REJECT交易數量和金額
        handleTotalMapByTrans(detailData);
        Map data =  new HashMap();
        data.put(RTN_KEY_DETAIL,getPageDataList(detailData,false));
        data.put(RTN_KEY_TOTAL,totalMap);
        return  PdfExportUtil.exprotPdf(data,templateFile,targetPdfFile);
    }

    @Override
    public int getPageSize() {
        return 20;
    }

}
