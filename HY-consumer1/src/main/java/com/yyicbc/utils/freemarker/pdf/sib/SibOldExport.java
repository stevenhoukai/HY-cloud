package com.yyicbc.utils.freemarker.pdf.sib;

import com.yyicbc.beans.enums.FovaPayStatus;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.CCustomDate;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.FileUtil;
import com.yyicbc.utils.freemarker.pdf.AbstractPdfExport;
import com.yyicbc.utils.freemarker.pdf.PdfExportUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameOldExport
 * @Description 社保旧
 * @Author vic
 * @Date2020/2/26 17:54
 * @Version V1.0
 **/
@Component
public class SibOldExport extends AbstractPdfExport {


    @Override
    protected List exportPdfList(List<FovaUpdateDataPO> detailData) throws Exception {
        return null;
    }

    @Override
    protected List exportTxtList(List<FovaUpdateDataPO> detailData) throws Exception {

        List fileList = new ArrayList<File>();

        setAmountUnit(false);
        //先進行匯總
        handleTotalMap(detailData);
        //按序号排序
        sortList(detailData);
        //失败记录TXT TYPE_BAD
        File apbTxtFile = exportTxt(filterData(detailData, TYPE_BAD), "icbapb" + transDateFileName6 + ".txt", TYPE_BAD);
        //成功记录TXT TYPE_PAY
        File apgTxtFile = exportTxt(filterData(detailData, TYPE_PAY), "icbapg" + transDateFileName6 + ".txt", TYPE_PAY);
        //失败记录DAT
        setAmountUnit(true);
        handleTotalMap(detailData);
        //失败
        File apbDatFile = exportDatToPdf(filterData(detailData, TYPE_BAD), "icbapb" + transDateFileName6 + "r", TYPE_BAD, "不成功");
        //成功
        File apgDatFile = exportDatToPdf(filterData(detailData, TYPE_PAY), "icbapg" + transDateFileName6 + "r", TYPE_PAY, "  成功");

        //对失败文件进行打包 生成icbapb1908.zip
        if (apbTxtFile != null && apbDatFile != null) {
            List<File> zipFileList = new ArrayList<File>();
            if (apbTxtFile != null) {
                zipFileList.add(apbTxtFile);
            }
            if (apbDatFile != null) {
                zipFileList.add(apbDatFile);
            }
            String zipName = "icbapb" + transDateFileName6;
            FileUtil.createZipByFileList(getPath(), zipFileList, zipName, false);
            File zipFile = new File(getPath() + zipName);
            addFileList(fileList, zipFile);
        }
        //对成功文件进行打包 生成icbapg1908.zip
        if (apgTxtFile != null && apgDatFile != null) {
            List<File> zipFileList = new ArrayList<File>();
            if (apbTxtFile != null) {
                zipFileList.add(apgTxtFile);
            }
            if (apbDatFile != null) {
                zipFileList.add(apgDatFile);
            }
            String zipName = "icbapg" + transDateFileName6;
            FileUtil.createZipByFileList(getPath(), zipFileList, zipName, false);
            File zipFile = new File(getPath() + zipName);
            addFileList(fileList, zipFile);
        }

        addFileList(fileList, apbTxtFile);
        addFileList(fileList, apbDatFile);
        addFileList(fileList, apgTxtFile);
        addFileList(fileList, apgDatFile);

        return fileList;

    }

    /**
     * 功能描述:处理序号，凭单编号
     *
     * @param detailData
     * @return:
     * @Author: vic
     * @Date: 2020/4/21 9:13
     */
    private void handleDetailList(List<FovaUpdateDataPO> detailData) {
        if (CollectionUtils.isNotEmpty(detailData)) {
            for (int i = 0; i < detailData.size(); i++) {
                FovaUpdateDataPO detail = detailData.get(i);
                String serNum = YyStringUtils.addZero((i + 1), 6);
                //导出PDF vo赋值不生效改成这个字段SeqNo
                /*detail.setSerNum(serNum);*/
                detail.setSeqNo(serNum);
                HashMap<String, String> detailMap = getMapByUnused(detail.getUnused());
                //凭单编号
                String fss_pdbh = YyStringUtils.toString(detailMap.get("fss_pdbh"));
                detail.setNotes1(YyStringUtils.addZero(fss_pdbh, 5));

                //不能執行原因
                String status = YyStringUtils.toString(detail.getStatus());
                if (!FovaPayStatus.PAY.getStatusCode().equals(status)) {
                    if (getAllDealMap().containsKey(status)) {
                        detail.setErrMessage(getAllDealMap().get(status).getSymbolName());
                    } else {
                        detail.setErrMessage(status);
                    }
                } else {
                    detail.setErrMessage("");
                }

            }
        }
    }


    /**
     * 功能描述:1、icbapb1908.zip（把以下兩個文件壓縮後的文檔名稱，需要加密後壓縮）
     * icbapb1908.txt（不成功的扣賬數據）
     * Header:
     * ICB000(固定位數)+失敗金額+失敗筆數+扣賬日期+社保賬號（開戶時固定賬號）
     * Detail:
     * 0000011+合同號碼+金額+扣賬賬號
     * icbapb1908r.dat（失敗後返回的報表格式）
     * 2、icbapg1908.zip（把以下兩個文件壓縮後的文檔名稱，需要加密後壓縮）
     * * icbapg1908.txt
     * * Header:
     * * ICB000(固定位數)+成功金額+成功筆數+扣賬日期+社保賬號（開戶時固定賬號）
     * * Detail:
     * * 0000011+合同號碼+金額+扣賬賬號
     * * icbapg1908r.dat（成功後返回的報表格式）    *
     *
     * @param detailData
     * @param fileName
     * @param type
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/2/27 17:23
     */
    private File exportTxt(List<FovaUpdateDataPO> detailData, String fileName, int type) throws Exception {
        //处理明细数据
        handleDetailList(detailData);
        StringBuffer txtBuffer = new StringBuffer();

        //Header
        txtBuffer.append("ICB");
        if (type == TYPE_BAD) {
            txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_REJECTAMOUNT), 11));
            txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_REJECTRECORD), 6));
        } else {
            txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_PAYMENTAMOUNT), 11));
            txtBuffer.append(YyStringUtils.addZero(getStringValue(totalMap, TOTAL_KEY_GOODDEBIT), 6));
        }
        txtBuffer.append(YyStringUtils.addSpaceByRight(transDate, 8));
        txtBuffer.append(YyStringUtils.addSpaceByRight(getCompanyAccount(), 20));
        //Detail
        if (CollectionUtils.isNotEmpty(detailData)) {
            for (int i = 0; i < detailData.size(); i++) {
                FovaUpdateDataPO detail = detailData.get(i);
                HashMap<String, String> detailMap = getMapByUnused(detail.getUnused());
                //序号
                txtBuffer.append("\r\n" + detail.getSeqNo());
                //合同号
                txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getCustomerContract(), 10));
                //凭单编号
                String fss_pdbh = YyStringUtils.toString(detailMap.get("fss_pdbh"));
                txtBuffer.append(detail.getNotes1());
                //金額
                txtBuffer.append(YyStringUtils.addZero(detail.getAmount(), 11));

                //付款人銀行帳號
                txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getCustomerAccount(), 20));

                //是否新格式
                if (isNew()) {
                    //受益人证件编号
                    String fss_syrzjbh = YyStringUtils.toString(detailMap.get("fss_syrzjbh"));
                    //凭单编号第5位=5 任意性供款
                    String s5 = getTypeByPdbh(fss_pdbh);
                    if ("5".equals(s5)) {
                        txtBuffer.append(YyStringUtils.addSpaceByRight(fss_syrzjbh, 20));
                    } else {
                        txtBuffer.append(YyStringUtils.addSpaceByRight("", 20));
                    }
                }

                //不能執行原因
                if (type == TYPE_BAD) {
                    txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getErrMessage(), 40));
                }
            }
        }
        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + fileName);
    }

    /**
     * 功能描述:凭单编号类别
     *
     * @param fss_pdbh
     * @return: {@link String}
     * @Author: vic
     * @Date: 2020/4/20 16:42
     */
    private String getTypeByPdbh(String fss_pdbh) {
        if (fss_pdbh.length() >= 5) {
            String s5 = fss_pdbh.substring(4, 5);
            return s5;
        }
        return "";
    }

    /**
     * 功能描述: 先处理序号，按序号进行排序
     *
     * @param detailData
     * @return:
     * @Author: vic
     * @Date: 2020/3/27 15:47
     */
    private void sortList(List<FovaUpdateDataPO> detailData) {

        if (CollectionUtils.isNotEmpty(detailData)) {
            for (FovaUpdateDataPO detail : detailData) {
                HashMap<String, String> detailMap = getMapByUnused(detail.getUnused());
                String fss_xh = YyStringUtils.toString(detailMap.get("fss_xh"));
                //临时存放序号，
                detail.setSeqNo(fss_xh);
            }
            //排序
            detailData.sort((o1, o2) -> (Integer.valueOf(o1.getSeqNo())).compareTo(Integer.valueOf(o2.getSeqNo())));
        }
    }

    /**
     * 功能描述:生成PDF
     *
     * @param detailData
     * @param fileName
     * @param type
     * @param title
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/4/20 16:13
     */
    private File exportDatToPdf(List<FovaUpdateDataPO> detailData, String fileName, int type, String title) {
        //处理明细数据
        handleDetailList(detailData);
        //根据类型分组合计
        handleFssTotalMap(detailData);

        Map data = new HashMap();
        //所有分頁後的明細數據
        data.put(RTN_KEY_DETAIL, getPageDataList(detailData, false));

        totalMap.put("title", title);
        data.put(RTN_KEY_TOTAL, totalMap);
        return PdfExportUtil.exprotPdf(data, "Sib", getPath() + fileName);
    }

    @Override
    public int getPageSize() {
        return 30;
    }

    /**
     * 功能描述:
     *
     * @param detailData
     * @return:
     * @Author: vic
     * @Date: 2020/4/20 17:13
     */
    private void handleFssTotalMap(List<FovaUpdateDataPO> detailData) {
        //任意性供款
        int ryxgkRecord = 0;
        Long ryxgkAmount = 0L;
        //外僱聘用費
        int wgpyfRecord = 0;
        Long wgpyfAmount = 0L;
        //強制性長工供款
        int qzxcgggRecord = 0;
        Long qzxcgggAmount = 0L;
        //強制性散工供款
        int qzxsgggRecord = 0;
        Long qzxsgggAmount = 0L;
        //总数
        int totalRecord = 0;
        Long totalAmount = 0L;

        if (CollectionUtils.isNotEmpty(detailData)) {
            for (FovaUpdateDataPO detail : detailData) {
                HashMap<String, String> detailMap = getMapByUnused(detail.getUnused());
                //凭单编号
                String fss_pdbh = YyStringUtils.toString(detailMap.get("fss_pdbh"));
                String pdbhType = getTypeByPdbh(fss_pdbh);
                if ("5".equals(pdbhType)) {
                    ryxgkRecord++;
                    ryxgkAmount += Long.parseLong(detail.getAmount());
                } else if ("4".equals(pdbhType)) {
                    wgpyfRecord++;
                    wgpyfAmount += Long.parseLong(detail.getAmount());
                } else if ("8".equals(pdbhType)) {
                    qzxcgggRecord++;
                    qzxcgggAmount += Long.parseLong(detail.getAmount());
                } else if ("9".equals(pdbhType)) {
                    qzxsgggRecord++;
                    qzxsgggAmount += Long.parseLong(detail.getAmount());
                }
                totalRecord++;
                totalAmount += Long.parseLong(detail.getAmount());
            }
        }

        totalMap.put("ryxgkRecord", ryxgkRecord);
        totalMap.put("ryxgkAmount", getAmountByUnit(ryxgkAmount));
        totalMap.put("wgpyfRecord", wgpyfRecord);
        totalMap.put("wgpyfAmount", getAmountByUnit(wgpyfAmount));
        totalMap.put("qzxcgggRecord", qzxcgggRecord);
        totalMap.put("qzxcgggAmount", getAmountByUnit(qzxcgggAmount));
        totalMap.put("qzxsgggRecord", qzxsgggRecord);
        totalMap.put("qzxsgggAmount", getAmountByUnit(qzxsgggAmount));
        totalMap.put("totalRecord", totalRecord);
        totalMap.put("totalAmount", getAmountByUnit(totalAmount));

    }

    /**
     * 功能描述:
     *
     * @param detailData 标题数据
     * @param fileName   文件名称
     * @param type       类型 失败或者成功
     * @param title      标题
     * @return: {@link java.io.File}
     * @Author: vic
     * @Date: 2020/2/27 17:20
     */
    private File exportDat(List<FovaUpdateDataPO> detailData, String fileName, int type, String title) {

        StringBuffer txtBuffer = new StringBuffer();
        txtBuffer.append("銀行名稱 : 中國工商銀行（澳門）股份有限公司                                     日期 : " + sysdatetime.substring(0, 10));
        txtBuffer.append("\r\n\r\n報表名稱 : 自動轉帳扣數報表 - " + title + "                                            時間 : " + sysdatetime.substring(10, sysdatetime.length()));
        txtBuffer.append("\r\n\r\n扣數日期 : " + CCustomDate.getFormatDateDef(transDate) + "                                                           頁數 : 1 ");
        String errorColName = type == TYPE_BAD ? "不能執行原因" : "";
        txtBuffer.append("\r\n\r\n序號       受益人/供款人編號     憑單編號            金額     帳號                     " + errorColName);
        txtBuffer.append("\r\n--------------------------------------------------------------------------------------------------------------------");
        if (CollectionUtils.isNotEmpty(detailData)) {
            for (FovaUpdateDataPO detail : detailData) {
                txtBuffer.append("\r\n");
                //序号
                txtBuffer.append(YyStringUtils.addSpaceByRight(YyStringUtils.addZero(detail.getSerNum(), 6), 11));
                //受益人/供款人編號
                txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getCustomerContract(), 22));
                //憑單編號
                txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getAgentNo(), 8));
                //金額
                txtBuffer.append(YyStringUtils.addSpaceByLeft(getAmountByUnit(detail.getAmount()), 16));
                txtBuffer.append(YyStringUtils.addSpaceByRight("", 5));
                //帳號
                txtBuffer.append(YyStringUtils.addSpaceByRight(detail.getCustomerAccount(), 25));
                //失败原因
                if (TYPE_BAD == type) {
                    txtBuffer.append(detail.getErrMessage());
                }
            }
        }
        txtBuffer.append("\r\n\r\nTOTAL");
        txtBuffer.append("\r\n--------------------------------------------------------------------------------------------------------------------");
        txtBuffer.append("\r\n任意性供款 :");
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0", 15));
        txtBuffer.append("        金額 :");
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0.00", 17));
        txtBuffer.append("\r\n外僱聘用費 :");
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0", 15));
        txtBuffer.append("        金額 :");
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0.00", 17));
        txtBuffer.append("\r\n強制性長工供款 :");
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0", 11));
        txtBuffer.append("        金額 :");
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0.00", 17));
        txtBuffer.append("\r\n強制性散工供款 :");
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0", 11));
        txtBuffer.append("        金額 :");
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0.00", 17));
        txtBuffer.append("\r\n總數 :");
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0", 21));
        txtBuffer.append("        總金額 :");
        txtBuffer.append(YyStringUtils.addSpaceByLeft("0.00", 15));
        return ExportDownLoad.generateTxtFile(txtBuffer.toString(), getPath() + fileName);
    }


    @Override
    protected List exportExcelList(List<FovaUpdateDataPO> detailData) {
        return null;
    }

    protected boolean isNew() {
        return false;
    }
}
