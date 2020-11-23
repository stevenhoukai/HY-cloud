package com.yyicbc.utils.freemarker.pdf;

import com.alibaba.fastjson.JSONArray;
import com.yyicbc.beans.business.PO.*;
import com.yyicbc.beans.enums.ExportTypeEnums;
import com.yyicbc.beans.enums.FovaPayStatus;
import com.yyicbc.beans.enums.ServiceStatusEnums;
import com.yyicbc.beans.enums.SpecialTemplateEnums;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.querycondition.CusetomerAgreementQueryVO;
import com.yyicbc.beans.utils.AmountUtil;
import com.yyicbc.beans.utils.CCustomDate;
import com.yyicbc.beans.utils.CopyBean;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;

/**
 * @author vic fu
 */
@Component
public abstract class AbstractPdfExport {

    /**
     * PDF文件路徑
     **/
    @Value("${export.path.pdf}")
    protected String EXPORT_PATH_PDF;

    /**
     * 按公司創建文件夾，
     **/
    protected String comFilePath;

    /**
     * 合計值
     **/
    protected Map totalMap;

    /**
     * 明細KEY
     **/
    protected final String RTN_KEY_DETAIL = "datalist";
    /**
     * 合計KEY
     **/
    protected final String RTN_KEY_TOTAL = "totaldata";
    /**
     * 總交易金額
     **/
    protected final String TOTAL_KEY_AMOUNTDUE = "amountDue";
    /**
     * 总交易数量
     **/
    protected final String TOTAL_KEY_RECORD = "record";
    /**
     * 成功交易筆數
     **/
    protected final String TOTAL_KEY_GOODDEBIT = "goodDebit";
    /**
     * 成功交易總金額
     **/
    protected final String TOTAL_KEY_PAYMENTAMOUNT = "paymentAmount";
    /**
     * 失敗交易筆數
     **/
    protected final String TOTAL_KEY_REJECTRECORD = "rejectRecord";
    /**
     * 失敗交易金額
     **/
    protected final String TOTAL_KEY_REJECTAMOUNT = "rejectAmount";
    /**
     * 新交易
     **/
    protected final String TOTAL_KEY_NEWSETUP = "newSetup";
    /**
     * 取消交易
     **/
    protected final String TOTAL_KEY_CANCELSERVICE = "cancelService";

    protected final String KEY_SYSDATETIME = "sysdatetime";
    protected final String KEY_PAGE = "page";
    protected final String KEY_CHILDLIST = "childList";


    /**
     * 所有错误处理标志
     **/
    protected HashMap<String, DealSymbolPO> allDealMap;
    /**
     * 交易日期文件名称 长度最后4位
     **/
    protected String transDateFileName;
    /**
     * 长度最后6位
     **/
    protected String transDateFileName6;
    /*币种*/
    protected String currType;
    /**
     * 交易日期
     */
    protected String transDate;

    /**
     * 系统时间
     **/
    protected String sysdatetime;

    /**
     * 公司账号：协议档案公司账号
     **/
    protected final String KEY_ACCOUNT_NO = "company_account";

    /*公司代码*/
    protected final String KEY_COMPANY_CODE = "company_code";
    /*公司名称*/
    protected final String KEY_COMPANY_NAME = "company_name";

    /*币种*/
    protected final String KEY_CURRTYPE = "currType";

    //交易日期：取入账日期
    protected final String KEY_TRANDATE = "tranDate";

    protected CompanyBasePO companyDetail;

    protected CompanyAgreementPO companyAgreementPO;


    @Value("${rest.location.prefix}")
    protected String REST_URL_PREFIX;

    @Autowired
    protected RestTemplate restTemplate;
    //每一頁條數
    protected int pageSize;
    /**
     * 金额是否根据单元转换
     */
    protected boolean isAmountUnit = true;

    //完整路徑
    protected String getPath() {
        return EXPORT_PATH_PDF + "/" + comFilePath + "/";
    }

    //获取所有公司协议档案，根据服务类型来判断
    protected Map<String, CusetomerAgreementPO> customerMap;

    protected int TYPE_PAY = 0;
    protected int TYPE_BAD = 1;
    protected int TYPE_NEW = 2;
    protected int TYPE_CANCEL = 3;
    /**
     * new +Cancel
     */
    protected int TYPE_NDR = 4;
    /**
     * PAY+BAD
     */
    protected int TYPE_PBR = 5;

    protected final int dateLength = 8;

    /*导出标准PDF 判断是否对外
     */
    protected boolean isout;

    protected String templateCode;

    /**
     * 到PDF 進行打包生成ZIP文件
     *
     * @param detailData
     * @param templateCode
     * @return
     */
    public File exportFile(List<FovaUpdateDataPO> detailData, String templateCode,
                           CompanyBasePO companyDetail, CompanyAgreementPO companyAgreementPO, int fileType) throws Exception {

        this.companyDetail = companyDetail;
        this.companyAgreementPO = companyAgreementPO;
        this.templateCode = templateCode;
        comFilePath = UUID.randomUUID().toString();
        //错误档案处理标志
        this.allDealMap = loadAllDealSymbopoMap();
        //過濾匯總標識
        List handleList = filterDataByRecordF(detailData);
        if (CollectionUtils.isEmpty(handleList)) {
            return null;
        }
        //标准格式进行排序，错误数据都放到最后,特殊格式不排序，导入和导出顺序保持一致
        boolean sortWrong = true;
        if (ExportTypeEnums.SPECIAL.getCode() == fileType) {
            sortWrong = false;
        }
        handleList = handleSortList(handleList, sortWrong);
        //处理错误信息
//        handleErrorMsg(handleList);
        //处理币种显示
        handleCurrType(handleList);
        //获取第一条数据为交易日期
        FovaUpdateDataPO firstVO = ((FovaUpdateDataPO) handleList.get(0));
        transDate = firstVO.getTranDate();
        if (StringUtils.isNotEmpty(transDate) && transDate.length() == dateLength) {
            transDateFileName = transDate.substring(transDate.length() - 4, transDate.length());
            transDateFileName6 = transDate.substring(transDate.length() - 6, transDate.length());
        }

        currType = firstVO.getCurrType();
        sysdatetime = CCustomDate.getPrintDateTime();

        List fileList = new ArrayList();

        if (ExportTypeEnums.SPECIAL.getCode() == fileType) {  //特殊格式
            //财政局不需要
            if (!SpecialTemplateEnums.CZJ.getCode().equals(templateCode)) {
                //获取所有公司协议档案，根据服务类型来判断
                customerMap = getCustomerGroupMap(handleList);
            }
            //TXT
            List txtList = exportTxtList(handleList);
            //PDF
            List pdfList = exportPdfList(handleList);
            //EXCEL
            List excelList = exportExcelList(handleList);
            if (CollectionUtils.isNotEmpty(txtList)) {
                fileList.addAll(txtList);
            }
            if (CollectionUtils.isNotEmpty(pdfList)) {
                fileList.addAll(pdfList);
            }
            if (CollectionUtils.isNotEmpty(excelList)) {
                fileList.addAll(excelList);
            }
        } else if (ExportTypeEnums.EXCEL.getCode() == fileType) { //标准格式Excel
            fileList = exportExcelList(handleList);
        } else if (fileType == ExportTypeEnums.PDF_OUT.getCode()) { //标准格式PDF 对外
            this.isout = true;
            fileList = exportPdfList(handleList);
        } else if (fileType == ExportTypeEnums.PDF_IN.getCode()) { //标准格式PDF 对外
            this.isout = false;
            fileList = exportPdfList(handleList);
        } else if (fileType == ExportTypeEnums.CUSTOMER.getCode()) {//客户通知书
            fileList = exportTxtList(handleList);
        }

        //导出文件进行压缩
        if (CollectionUtils.isNotEmpty(fileList)) {
            String filename = templateCode + ".zip";
            FileUtil.fileToZip(getPath(), getPath(), filename);
            File zipFile = new File(getPath() + filename);
            return zipFile;
        }

        return null;

    }


    /**
     * 功能描述: 标准格式的PDF文件
     *
     * @param detailData
     * @return: {@link List}
     * @Author: vic
     * @Date: 2020/3/2 14:58
     */
    protected List exportStandardPdfList(List<FovaUpdateDataPO> detailData) throws Exception {

        return null;
    }

    /**
     * 功能描述: 标准格式的EXCEL文件
     *
     * @param detailData
     * @return: {@link List}
     * @Author: vic
     * @Date: 2020/3/2 14:58
     */
    protected List exportStandardExcelList(List<FovaUpdateDataPO> detailData) throws Exception {
        return null;
    }

    /**
     * 功能描述: 标准格式的客户通知书
     *
     * @param detailData
     * @return: {@link List}
     * @Author: vic
     * @Date: 2020/3/2 14:58
     */
    protected List exportStandardCustomerList(List<FovaUpdateDataPO> detailData) throws Exception {

        return null;
    }

    /**
     * 功能描述:導出多個PDF文件
     *
     * @param detailData
     * @return: {@link List}
     * @Author: vic
     * @Date: 2020/3/12 10:48
     */
    protected abstract List exportPdfList(List<FovaUpdateDataPO> detailData) throws Exception;

    /**
     * 導出多個TXT文件
     *
     * @param detailData
     * @return
     */
    protected abstract List exportTxtList(List<FovaUpdateDataPO> detailData) throws Exception;

    /**
     * 導出多個Excel文件
     *
     * @param detailData
     * @return
     */
    protected abstract List exportExcelList(List<FovaUpdateDataPO> detailData) throws Exception;


    public Map getTotalMap() {
        return totalMap;
    }

    /**
     * 明細進行匯總
     *
     * @param detailData
     * @return
     */
    protected Map handleTotalMap(List<FovaUpdateDataPO> detailData) {

        totalMap = new HashMap();

        //总数量
        int record = 0;
        //总金额（包括错误PAY+BAD金额）
        Long amountDue = 0L;
        //pay 数量
        int goodDebit = 0;
        Long paymentAmount = 0L;
        //bad数量
        int rejectRecord = 0;
        //bad 金额
        Long rejectAmount = 0L;
        //new setup 數量
        int newSetup = 0;
        //cancel數量
        int cancelService = 0;


        for (FovaUpdateDataPO po : detailData) {

            amountDue += Long.parseLong(po.getAmount());
            record++;

            String customerAccount = po.getCustomerAccount() == null ? "" : po.getCustomerAccount();

            CusetomerAgreementPO custPO = getCustomerMap().get(customerAccount);

            if (custPO != null && ServiceStatusEnums.NEW.getValue().equals(custPO.getServiceStatus())) {//new
                newSetup++;
            } else if (custPO != null && ServiceStatusEnums.CANCEL.getValue().equals(custPO.getServiceStatus())) {//cancel
                cancelService++;
            }
            //PAY : CZJ 財政局特殊處理
            if (SpecialTemplateEnums.CZJ.getCode().equals(templateCode)) {
                if (FovaPayStatus.PAY.getStatusCode().equals(po.getStatusDsf())) {
                    paymentAmount += Long.parseLong(po.getAmount());
                    goodDebit++;
                } else {//BAD
                    rejectRecord++;
                    rejectAmount += Long.parseLong(po.getAmount());
                }
            } else {
                if (FovaPayStatus.PAY.getStatusCode().equals(po.getStatus())) {
                    paymentAmount += Long.parseLong(po.getAmount());
                    goodDebit++;
                } else {//BAD
                    rejectRecord++;
                    rejectAmount += Long.parseLong(po.getAmount());
                }
            }

        }
        //總金額
        totalMap.put(TOTAL_KEY_RECORD, record);
        totalMap.put(TOTAL_KEY_AMOUNTDUE, getAmountByUnit(amountDue));

        //成功交易
        totalMap.put(TOTAL_KEY_GOODDEBIT, goodDebit);
        totalMap.put(TOTAL_KEY_PAYMENTAMOUNT, getAmountByUnit(paymentAmount));

        //失敗交易
        totalMap.put(TOTAL_KEY_REJECTRECORD, rejectRecord);
        totalMap.put(TOTAL_KEY_REJECTAMOUNT, getAmountByUnit(rejectAmount));
        //新交易
        totalMap.put(TOTAL_KEY_NEWSETUP, newSetup);
        //取消交易
        totalMap.put(TOTAL_KEY_CANCELSERVICE, cancelService);


        return totalMap;
    }

    /**
     * 根據狀態過濾數據
     *
     * @param detailData
     * @param type
     * @return
     */
    protected List filterData(List<FovaUpdateDataPO> detailData, int type) {
        List list = new ArrayList();
        detailData.forEach(po -> {
            String customerAccount = po.getCustomerAccount() == null ? "" : po.getCustomerAccount();

            CusetomerAgreementPO custPO = customerMap.get(customerAccount);

            switch (type) {
                case 0://PAY
                    /*if (custPO != null && StringUtils.isNotBlank(custPO.getServiceStatus()) &&
                            (custPO.getServiceStatus().equals(ServiceStatusEnums.NEW.getValue())
                                    || custPO.getServiceStatus().equals(ServiceStatusEnums.CANCEL.getValue()))) {

                    } else {*/
                    if ((StringUtils.isNotBlank(po.getStatus()) &&
                            po.getStatus().equals(FovaPayStatus.PAY.getStatusCode()))) {
                        list.add(po);
                    }
                    //}
                    break;
                case 1://BAD
                    /*if (custPO != null && StringUtils.isNotBlank(custPO.getServiceStatus()) &&
                            (custPO.getServiceStatus().equals(ServiceStatusEnums.NEW.getValue())
                                    || custPO.getServiceStatus().equals(ServiceStatusEnums.CANCEL.getValue()))) {

                    } else {*/
                    if ((StringUtils.isNotBlank(po.getStatus()) && !po.getStatus().equals(FovaPayStatus.PAY.getStatusCode()))) {
                        list.add(po);
                    }
                    /*}*/
                    break;
                case 2://NEW
                    if (custPO != null && StringUtils.isNotBlank(custPO.getServiceStatus()) &&
                            custPO.getServiceStatus().equals(ServiceStatusEnums.NEW.getValue())) {
                        list.add(po);
                    }
                    break;
                case 3://CANCEL
                    if (custPO != null && StringUtils.isNotBlank(custPO.getServiceStatus()) &&
                            custPO.getServiceStatus().equals(ServiceStatusEnums.CANCEL.getValue())) {
                        list.add(po);
                    }
                    break;
                case 4://NDR 包括 NEW 和Cancel
                    if (custPO != null && StringUtils.isNotBlank(custPO.getServiceStatus()) && (
                            custPO.getServiceStatus().equals(ServiceStatusEnums.NEW.getValue())
                                    || custPO.getServiceStatus().equals(ServiceStatusEnums.CANCEL.getValue()))) {
                        list.add(po);
                    }
                    break;
                case 5://PBR 包括付款成功和失败
                    if (custPO != null && StringUtils.isNotBlank(custPO.getServiceStatus()) && (
                            custPO.getServiceStatus().equals(ServiceStatusEnums.NEW.getValue())
                                    || custPO.getServiceStatus().equals(ServiceStatusEnums.CANCEL.getValue()))) {

                    } else {
                        list.add(po);
                    }
                    break;
                default:
                    break;
            }
        });
        return list;
    }


    /**
     * 過濾匯總
     *
     * @param detailData
     * @return
     */
    protected List filterDataByRecordF(List<FovaUpdateDataPO> detailData) {
        List list = new ArrayList();
        for (FovaUpdateDataPO po : detailData) {
            //過濾總數
            if ((!StringUtils.isBlank(po.getRecordF()) && "1".equals(po.getRecordF()))) {
                continue;
            }
            list.add(po);
        }
        return list;
    }


    /**
     * 獲取分頁數據
     *
     * @param detailData
     * @param addTotal   合计行单独加一页
     * @return
     */
    protected List getPageDataList(List<FovaUpdateDataPO> detailData, boolean addTotal) {
        //處理金額的顯示
        List<FovaUpdateDataPO> handleList = handleAmountByUnit(detailData);
        //進行分頁
        int totalCount = handleList.size();
        int pageSize = getPageSize();
        int pageIndex = 0;
        int pageCount = 0;
        int m = totalCount % pageSize;
        if (m > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }
        //分页数据
        List pageDataList = new ArrayList();

        String sysdatetime = CCustomDate.getPrintDateTime();

        for (int i = 1; i <= pageCount; i++) {
            List<FovaUpdateDataPO> subList = null;
            if (i == pageCount) {
                subList = handleList.subList((i - 1) * pageSize, totalCount);
                pageDataList.add(getChildMap(sysdatetime, i, transDate, subList));
            } else {
                subList = handleList.subList((i - 1) * pageSize, pageSize * (i));
                pageDataList.add(getChildMap(sysdatetime, i, transDate, subList));
            }
        }

        if (m == 0) {
            pageDataList.add(getChildMap(sysdatetime,
                    pageCount + 1, transDate, new ArrayList()));
        }


        return pageDataList;

    }

    /**
     * 根據單位格式化金額
     *
     * @param detailData
     */
    private List<FovaUpdateDataPO> handleAmountByUnit(List<FovaUpdateDataPO> detailData) {
        List<FovaUpdateDataPO> handList = new ArrayList<FovaUpdateDataPO>();
        for (FovaUpdateDataPO dataPO : detailData) {
            FovaUpdateDataPO clonePO = new FovaUpdateDataPO();
            clonePO = (FovaUpdateDataPO) CopyBean.Copy(clonePO, dataPO, true);
            clonePO.setAmount(getAmountByUnit(dataPO.getAmount()));
            handList.add(clonePO);
        }
        return handList;
    }


    protected String getAmountByUnit(Object amount) {
        if (isAmountUnit) {
            return AmountUtil.getAmountByUnit(companyDetail, amount);
        } else {
            if (amount == null) {
                return "";
            }
            return amount.toString();
        }
    }

    protected String getStringValue(Map map, String key) {
        return map.get(key) == null ? "" : map.get(key).toString();
    }

    /**
     * 單頁表格數據
     *
     * @param sysdatetime
     * @param pageIndex
     * @param trandate
     * @param subList
     * @return
     */
    protected Map getChildMap(String sysdatetime, int pageIndex, String trandate, List subList) {
        Map childMap = new HashMap();
        childMap.put(KEY_SYSDATETIME, sysdatetime);
        childMap.put("date", sysdatetime.substring(0, 10));
        childMap.put("time", sysdatetime.substring(11, sysdatetime.length()));
        childMap.put(KEY_PAGE, pageIndex);
        //交易日期
        //转成指定格式
        trandate = CCustomDate.getFormatDateDef(trandate);
        childMap.put(KEY_TRANDATE, trandate);
        //公司账号,companyAgreementPO.getCompanyAccount()
        childMap.put(KEY_ACCOUNT_NO, getCompanyAccount());
        //公司代码
        childMap.put(KEY_COMPANY_CODE, getCompanyAgreementPO().getCompanyCode());
        //公司名称
        childMap.put(KEY_COMPANY_NAME, getCompanyAgreementPO().getCompanyName());

        childMap.put(KEY_CHILDLIST, subList);
        return childMap;
    }

    /**
     * 公司账号
     *
     * @return
     */
    protected String getCompanyAccount() {
        //公司账号,companyAgreementPO.getCompanyAccount()
        String company_account = "";
        if (companyAgreementPO != null) {
            company_account = companyAgreementPO.getCompanyAccount();
        }
        return company_account;
    }

    /**
     * 公司名称
     *
     * @return
     */
    protected String getCompanyName() {
        //公司账号,companyAgreementPO.getCompanyAccount()
        return StringUtils.isEmpty(getCompanyAgreementPO().getCompanyName()) ? "" : getCompanyAgreementPO().getCompanyName();
    }

    /**
     * 功能描述:
     *
     * @param
     * @return: {@link com.yyicbc.beans.business.PO.CompanyAgreementPO}
     * @Author: vic
     * @Date: 2020/3/2 15:44
     */
    public CompanyAgreementPO getCompanyAgreementPO() {
        if (companyAgreementPO == null) {
            companyAgreementPO = new CompanyAgreementPO();
        }
        return companyAgreementPO;
    }


    public int getPageSize() {
        return 30;
    }

    public void setCompanyDetail(CompanyBasePO companyDetail) {
        this.companyDetail = companyDetail;
    }

    public List addFileList(List list, File file) {
        if (list != null) {
            if (file != null) {
                list.add(file);
            }
        }
        return list;
    }

    /**
     * 获取公司客户档案,KEY=客户账号
     *
     * @return
     */
    protected Map<String, CusetomerAgreementPO> getCustomerGroupMap(List<FovaUpdateDataPO> detailData) {

        //组装查询条件
        CusetomerAgreementQueryVO request = new CusetomerAgreementQueryVO();
        List<String> accountList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(detailData)) {
            detailData.forEach(fovaUpdateDataPO -> {
                accountList.add(fovaUpdateDataPO.getCustomerAccount());
            });
        }
        request.setAccoutnList(accountList);
        //如果是CTM模板需要查询公司协议
        String customerAgree = restTemplate.postForObject(REST_URL_PREFIX + "/collection/customerAgreementFile/findByfowaAccount", request, String.class);
        Map map = new HashMap<String, CusetomerAgreementPO>();
        if (StringUtils.isEmpty(customerAgree)) {
            return map;
        }
        List<CusetomerAgreementPO> customerAgreeList = JSONArray.parseArray(customerAgree).toJavaList(CusetomerAgreementPO.class);

        if (CollectionUtils.isNotEmpty(customerAgreeList)) {
            customerAgreeList.forEach(cusetomerAgreementPO -> {
                map.put(cusetomerAgreementPO.getFowaAccount(), cusetomerAgreementPO);
            });
        }
        return map;
    }

    /**
     * 功能描述:金额是否根据单元转换
     *
     * @param amountUnit 默认true,false 不转换
     * @return:
     * @Author: vic
     * @Date: 2020/2/26 18:37
     */
    public void setAmountUnit(boolean amountUnit) {
        isAmountUnit = amountUnit;
    }

    public Map<String, CusetomerAgreementPO> getCustomerMap() {
        return customerMap == null ? new HashMap<String, CusetomerAgreementPO>() : customerMap;
    }

    /**
     * 功能描述: 对字段进行拆分
     * dsf_jllb:D,dsf_yhdh:37,dsf_czjckbh:201700008001002377000000000100,dsf_smsbhnf:2016,
     * dsf_smsbh:00000002627932,dsf_smsbhzxh:00,dsf_skrpwmc:CHAN MIO KUN,
     *
     * @param unused
     * @return: {@link HashMap< String, String>}
     * @Author: vic
     * @Date: 2020/3/12 9:13
     */
    protected HashMap<String, String> getMapByUnused(String unused) {

        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(unused)) {
            String[] splits = unused.trim().split(",");
            for (String str : splits) {
                String[] values = str.split(":");
                if (values != null && values.length == 2) {
                    map.put(values[0], values[1]);
                }
            }
        }
        return map;
    }

    /**
     * 功能描述:获取所有处理标志
     *
     * @return: {@link java.util.HashMap<java.lang.String,com.yyicbc.beans.business.PO.DealSymbolPO>}
     * @Author: vic
     * @Date: 2020/3/27 17:35
     */
    protected HashMap<String, DealSymbolPO> loadAllDealSymbopoMap() {
        HashMap<String, DealSymbolPO> map = new HashMap<>();
        String res = restTemplate.getForObject(REST_URL_PREFIX + "/collection/dealSymbol/getAll", String.class);
        if (StringUtils.isEmpty(res)) {
            return map;
        }
        List<DealSymbolPO> list = JSONArray.parseArray(res).toJavaList(DealSymbolPO.class);

        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(detail -> {
                map.put(detail.getSymbolCode(), detail);
            });
        }
        return map;
    }

    public HashMap<String, DealSymbolPO> getAllDealMap() {
        return allDealMap == null ? new HashMap<String, DealSymbolPO>() : allDealMap;
    }


    /**
     * 功能描述: 根据业务类型处理合同号
     *
     * @param detailData
     * @return:
     * @Author: vic
     * @Date: 2020/4/2 11:04
     */
    protected void handleContract(List<FovaUpdateDataPO> detailData) {
        for (FovaUpdateDataPO po : detailData) {
            if ("001".equals(getCompanyAgreementPO().getBusinessTypeCode()) ||
                    "013".equals(getCompanyAgreementPO().getBusinessTypeCode())) {
                po.setCustomerContract("");
            }
        }
    }

    /**
     * 功能描述:
     *
     * @param detailData
     * @return:
     * @Author: vic
     * @Date: 2020/4/22 9:20
     */
    private void handleErrorMsg(List<FovaUpdateDataPO> detailData) {
        if ("dsf".equals(companyAgreementPO.getCompanyEncode().toLowerCase())) {
            for (FovaUpdateDataPO po : detailData) {
                po.setErrMessage(po.getErrMessageDsf());
            }
        }
    }

    /**
     * 功能描述: 错误数据都放到最后
     * 按序号进行排序
     *
     * @param detailData
     * @param sortWrong  错误数据是否放最后
     * @return:
     * @Author: vic
     * @Date: 2020/4/7 15:20
     */
    private List handleSortList(List<FovaUpdateDataPO> detailData, boolean sortWrong) {

        if (!sortWrong) {
            //按序号进行排序,转成整形排序
            detailData.sort((o1, o2) -> (Integer.valueOf(YyStringUtils.toString(o1.getSerNum()))).
                    compareTo(Integer.valueOf(YyStringUtils.toString(o2.getSerNum()))));
            return detailData;
        }

        List allList = new ArrayList();
        //付款成功数据
        List<FovaUpdateDataPO> okList = new ArrayList<FovaUpdateDataPO>();
        //错误数据
        List<FovaUpdateDataPO> errList = new ArrayList<FovaUpdateDataPO>();

        for (FovaUpdateDataPO po : detailData) {
            if (FovaPayStatus.PAY.getStatusCode().equals(YyStringUtils.toString(po.getStatus()))) {
                okList.add(po);
            } else {
                errList.add(po);
            }
        }
        if (CollectionUtils.isNotEmpty(okList)) {
            //按序号进行排序
            okList.sort((o1, o2) -> (Integer.valueOf(YyStringUtils.toString(o1.getSerNum()))).compareTo(Integer.valueOf(YyStringUtils.toString(o2.getSerNum()))));
            allList.addAll(okList);
        }
        if (CollectionUtils.isNotEmpty(errList)) {
            //按序号进行排序
            errList.sort((o1, o2) -> (Integer.valueOf(YyStringUtils.toString(o1.getSerNum()))).compareTo(Integer.valueOf(YyStringUtils.toString(o2.getSerNum()))));
            allList.addAll(errList);
        }
        return allList;
    }

    /**
     * 功能描述: 处理币种显示，显示英文编码
     *
     * @param detailData
     * @return:
     * @Author: vic
     * @Date: 2020/4/2 11:08
     */
    protected void handleCurrType(List<FovaUpdateDataPO> detailData) {

        HashMap<String, CurrencyBasePO> currencyMap = loadCurrTypeMap();

        for (FovaUpdateDataPO po : detailData) {

            po.setCurrType(currencyMap.get(po.getCurrType()) == null ? "" : currencyMap.get(po.getCurrType()).getCurrencyEn());

        }

    }

    /**
     * 功能描述:加载全部币种
     *
     * @param
     * @return: {@link HashMap< String, CurrencyBasePO>}
     * @Author: vic
     * @Date: 2020/4/2 14:39
     */
    protected HashMap<String, CurrencyBasePO> loadCurrTypeMap() {
        HashMap<String, CurrencyBasePO> map = new HashMap<>();
        String res = restTemplate.getForObject(REST_URL_PREFIX + "/collection/currencyFile/getAll", String.class);
        if (StringUtils.isEmpty(res)) {
            return map;
        }
        List<CurrencyBasePO> list = JSONArray.parseArray(res, CurrencyBasePO.class);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(detail -> {
                map.put(detail.getCurrencyCode(), detail);
            });
        }
        return map;
    }
}
