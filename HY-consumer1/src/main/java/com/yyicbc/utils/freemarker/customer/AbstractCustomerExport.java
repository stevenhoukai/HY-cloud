package com.yyicbc.utils.freemarker.customer;

import com.alibaba.fastjson.JSONArray;
import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.business.PO.DealSymbolPO;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.querycondition.CompanyQuestVO;
import com.yyicbc.beans.utils.CCustomDate;
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
 * @ClassNameCustomerExport
 * @Description
 * @Author vic
 * @Date2020/3/25 11:29
 * @Version V1.0
 **/

@Component
public abstract class AbstractCustomerExport {

    /**
     * 临时文件路径
     **/
    @Value("${export.path.pdf}")
    protected String EXPORT_PATH;
    @Value("${rest.location.prefix}")
    protected String REST_URL_PREFIX;

    @Autowired
    protected RestTemplate restTemplate;
    /**
     * 临时文件夹
     **/
    protected String tempFilePath;

    protected final String KEY_SYSDATETIME = "sysdatetime";
    protected final String KEY_PAGE = "page";
    protected final String KEY_CHILDLIST = "childList";
    /**
     * 总数
     */
    protected final String KEY_TOTAL_A = "total_a";
    protected final String KEY_TOTAL_N = "total_n";
    protected final String KEY_TOTAL_C = "total_c";
    protected final String KEY_TOTAL_M = "total_m";
    /**
     * 明細KEY
     **/
    protected final String RTN_KEY_DETAIL = "datalist";
    /**
     * 合計KEY
     **/
    protected final String RTN_KEY_TOTAL = "totaldata";

    protected abstract List exportZipList(List<CusetomerAgreementPO> list, CompanyQuestVO request);

    /**
     * 功能描述:
     *
     * @param list
     * @param request
     * @return: {@link File}
     * @Author: vic
     * @Date: 2020/3/25 14:14
     */
    public File exportFile(List<CusetomerAgreementPO> list, CompanyQuestVO request) throws Exception {

        tempFilePath = UUID.randomUUID().toString();
        //导出zip
        List fileList = exportZipList(list, request);
        //导出文件进行压缩
        if (CollectionUtils.isNotEmpty(fileList)) {
            String filename = request.getCompanyCode() + ".zip";
            FileUtil.createZipByFileList(getPath(), fileList, request.getCompanyCode(), true);
            File zipFile = new File(getPath() + filename);
            return zipFile;
        }
        return null;

    }

    /**
     * 功能描述: 根据日期进行分组
     *
     * @param list
     * @return: {@link HashMap< String, List< CusetomerAgreementPO>>}
     * @Author: vic
     * @Date: 2020/3/25 14:17
     */
    protected HashMap<String, List<CusetomerAgreementPO>> handListByDate(List<CusetomerAgreementPO> list) {

        HashMap<String, List<CusetomerAgreementPO>> map = new LinkedHashMap<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (CusetomerAgreementPO po : list) {
                if (StringUtils.isBlank(YyStringUtils.toString(po.getGenerateDate()))) {
                    continue;
                }
                String key = CCustomDate.getDateStr(po.getGenerateDate());
                List<CusetomerAgreementPO> groupList = new ArrayList<>();
                if (map.containsKey(key)) {
                    groupList = map.get(key);
                }
                groupList.add(po);
                map.put(key, groupList);
            }
        }
        return map;
    }

    /**
     * 功能描述: 获取服务状态
     *
     * @param serviceStatus
     * @return: {@link String}
     * @Author: vic
     * @Date: 2020/3/25 17:16
     */
    protected String getServiceStatus(String serviceStatus) {
        if ("0".equals(serviceStatus)) {
            return "N";
        } else if ("1".equals(serviceStatus)) {
            return "C";
        } else {
            return "M";
        }
    }


    //完整路徑
    protected String getPath() {
        return EXPORT_PATH + "/" + tempFilePath + "/";
    }


    /**
     * 功能描述:單頁表格數據
     *
     * @param sysdatetime
     * @param pageIndex
     * @param subList
     * @return: {@link Map}
     * @Author: vic
     * @Date: 2020/3/27 10:32
     */
    protected Map getChildMap(String sysdatetime, int pageIndex, List subList) {
        Map childMap = new HashMap();
        childMap.put(KEY_PAGE, pageIndex);
        //交易日期
        //转成指定格式
        childMap.put("date", CCustomDate.replaceSplitDef(sysdatetime.substring(0, 10)));
        childMap.put("time", sysdatetime.substring(sysdatetime.length() - 9, sysdatetime.length()));

        childMap.put(KEY_CHILDLIST, subList);
        return childMap;
    }

    /**
     * 功能描述:獲取分頁數據
     *
     * @param detailData
     * @return: {@link List}
     * @Author: vic
     * @Date: 2020/3/27 10:07
     */
    protected List getPageDataList(List<CusetomerAgreementPO> detailData) {
        //處理金額的顯示
        List<CusetomerAgreementPO> handleList = detailData;

        if (CollectionUtils.isEmpty(handleList)) {
            handleList = new ArrayList<CusetomerAgreementPO>();
            CusetomerAgreementPO defaultVO = new CusetomerAgreementPO();
            //去掉默认值
            defaultVO.setServiceStatus("");
            handleList.add(defaultVO);
        }
        //進行分頁
        int totalCount = handleList.size();
        int pageSize = getPageSize();
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

            List<CusetomerAgreementPO> subList = null;
            if (i == pageCount) {
                subList = handleList.subList((i - 1) * pageSize, totalCount);
                pageDataList.add(getChildMap(sysdatetime, i, subList));
            } else {
                subList = handleList.subList((i - 1) * pageSize, pageSize * (i));
                pageDataList.add(getChildMap(sysdatetime, i, subList));
            }
        }

        return pageDataList;

    }

    public int getPageSize() {
        return 30;
    }


    /**
     * 功能描述:其他特殊处理
     *
     * @param list
     * @return:
     * @Author: vic
     * @Date: 2020/3/27 11:06
     */
    protected void handleListOther(List<CusetomerAgreementPO> list) {

        for (int i = 0; i < list.size(); i++) {
            CusetomerAgreementPO po = list.get(i);
            //序号
            po.setAccountIndex(YyStringUtils.addZero(i + 1, 6));
            //服务
            po.setServiceStatus(getServiceStatus(po.getServiceStatus()));
            //申请日期 ，随便用一个字符串类型
            po.setOldAccount(YyStringUtils.repleacDate(CCustomDate.getDateStr(po.getGenerateDate())));

            //名称太长自动换行
            po.setAccountName(YyStringUtils.addHtmlBrByName(po.getAccountName(),15));
        }
    }



    /**
     * 功能描述:处理合计
     *
     * @param list
     * @return: {@link HashMap}
     * @Author: vic
     * @Date: 2020/3/27 10:36
     */
    protected HashMap handleTotalMap(List<CusetomerAgreementPO> list) {
        HashMap totalMap = new HashMap();

        int totala = 0;
        int totaln = 0;
        int totalc = 0;
        int totalm = 0;

        if (CollectionUtils.isNotEmpty(list)) {

            for (int i = 0; i < list.size(); i++) {
                CusetomerAgreementPO po = list.get(i);
                if (StringUtils.isNotEmpty(po.getServiceStatus())) {
                    totala++;
                    if ("0".equals(po.getServiceStatus())) {
                        totaln++;
                    } else if ("1".equals(po.getServiceStatus())) {
                        totalc++;
                    } else {
                        totalm++;
                    }
                }
            }
        }

        totalMap.put(KEY_TOTAL_A, totala);
        totalMap.put(KEY_TOTAL_N, totaln);
        totalMap.put(KEY_TOTAL_C, totalc);
        totalMap.put(KEY_TOTAL_M, totalm);

        return totalMap;
    }

}
