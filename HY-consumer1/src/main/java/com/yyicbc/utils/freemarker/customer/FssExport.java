package com.yyicbc.utils.freemarker.customer;

import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.querycondition.CompanyQuestVO;
import com.yyicbc.beans.utils.CCustomDate;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.FileUtil;
import com.yyicbc.utils.freemarker.pdf.PdfExportUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameFssExport
 * @Description
 * @Author vic
 * @Date2020/3/25 11:28
 * @Version V1.0
 **/

@Component
public class FssExport extends AbstractCustomerExport {


    /**
     * 功能描述: 按天数生成ZIP个数
     *
     * @param request
     * @return: {@link List}
     * @Author: vic
     * @Date: 2020/3/25 11:50
     */
    @Override
    protected List exportZipList(List<CusetomerAgreementPO> list, CompanyQuestVO request) {

        //先进行分组
        HashMap<String, List<CusetomerAgreementPO>> groupMap = handListByDate(list);

        //按照之前的规则把日期区间取出来
        String[] dateScope = CCustomDate.getDateScope(request.getGenerateDate());
        List fileList = new ArrayList<>();
        //不管有没有数据都要按每一天日期生成文件
        if (dateScope != null && dateScope.length == 2) {
            List<String> dates = CCustomDate.getDates(dateScope[0], dateScope[1]);
            for (String date : dates) {
                List dateList = groupMap.get(date) == null ? new ArrayList<CusetomerAgreementPO>() : groupMap.get(date);
                File zipFile = exportZip(dateList, request, date);
                fileList.add(zipFile);
            }
        }
        return fileList;
    }


    /**
     * 功能描述:功能描述:导出每一天的ZIP
     *
     * @param list
     * @param request
     * @param exportDate
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/3/25 12:03
     */
    protected File exportZip(List<CusetomerAgreementPO> list, CompanyQuestVO request, String exportDate) {

        String filename = exportDate + ".zip";

        //导出txt
        File txtFile = exportTxt(list, request, exportDate);

        //导出PDF
        File pdfFile = exportPdf(list, request, exportDate);

        //进行压缩
        FileUtil.fileToZip(addPathByDate(exportDate), addPathByDate(exportDate), filename);

        //返回ZIP文件
        File zipFile = new File(addPathByDate(exportDate) + filename);

        return zipFile;
    }

    /**
     * 功能描述:路径添加日期
     *
     * @param exportDate
     * @return: {@link String}
     * @Author: vic
     * @Date: 2020/3/25 12:23
     */
    protected String addPathByDate(String exportDate) {

        return getPath() + "/" + exportDate + "/";
    }


    /**
     * 功能描述:导出TXT
     *
     * @param list
     * @param request
     * @return:
     * @Author: vic
     * @Date: 2020/3/25 11:00
     */
    protected File exportTxt(List<CusetomerAgreementPO> list, CompanyQuestVO request, String exportDate) {

        StringBuffer txtBuffer = new StringBuffer();

        if (CollectionUtils.isNotEmpty(list)) {

            for (int i = 0; i < list.size(); i++) {
                CusetomerAgreementPO detail = list.get(i);
                //序号
                txtBuffer.append(YyStringUtils.addZero(i + 1, 6));
                //10位合同号
                txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getBillNumber(), 10));
                //19位账号
                txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getFowaAccount(), 19));
                //1个空格
                txtBuffer.append(" ");
                //受益人ID
                txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getBeneficiaryId(), 20));
                //姓名
                txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getAccountName(), 30));
                //1个空格
//                txtBuffer.append(" ");
                //导出日期
                txtBuffer.append(CCustomDate.replace(exportDate));
                //状态
                txtBuffer.append(getServiceStatus(detail.getServiceStatus()));

                txtBuffer.append("\n");
            }
        }

        //生成TXT
        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), addPathByDate(exportDate) + "icbapp" + CCustomDate.replace(exportDate) + ".TXT");


    }

    /**
     * 功能描述:导出PDF
     *
     * @param list
     * @param request
     * @return:
     * @Author: vic
     * @Date: 2020/3/25 11:01
     */
    protected File exportPdf(List<CusetomerAgreementPO> list, CompanyQuestVO request, String exportDate) {

        //合计
        HashMap totalMap = handleTotalMap(list);
        //其他字段处理
        handleListOther(list);

        Map data = new HashMap();
        data.put(RTN_KEY_DETAIL, getPageDataList(list));
        data.put(RTN_KEY_TOTAL, totalMap);
        return PdfExportUtil.exprotPdf(data, "NewApply", addPathByDate(exportDate) + "icbapp" + CCustomDate.replace(exportDate) + "r");
    }


}
