package com.yyicbc.controller;

import com.alibaba.fastjson.JSONArray;
import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.enums.StatusEnums;
import com.yyicbc.beans.imports.PO.GenerateImportPO;
import com.yyicbc.beans.querycondition.GenerateImportQuestVO;
import com.yyicbc.beans.utils.CCustomDate;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.utils.ExportDownLoad;
import com.yyicbc.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassNameGenrateImportController_Consumer
 * @Description
 * @Author vic
 * @Date2020/4/2 18:15
 * @Version V1.0
 **/
@RestController
@RequestMapping("/imports/generateImport")
@Slf4j
public class GenrateImportController_Consumer extends BaseController {

    /**功能描述:获取导入历史日期的历史记录
    * @return: {@link String}
    * @Author: vic
    * @Date: 2020/5/6 11:01
    */
    @RequestMapping("/getDateList")
    public String getDateList() {
        return res(new GenerateImportQuestVO(),"/imports/generateImport/getDateList");
    }


    /**功能描述:导出TXT
     * @param request
    * @return: {@link byte[]}
    * @Author: vic
    * @Date: 2020/5/6 11:01
    */
    @RequestMapping("/exportTxt")
    public byte[] exportTxt(@RequestBody GenerateImportQuestVO request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GenerateImportQuestVO> req = new HttpEntity<GenerateImportQuestVO>(request, headers);

        String res = restTemplate.postForObject(REST_URL_PREFIX + "/imports/generateImport/getImportData", req, String.class);

        RetData resData = JsonUtils.jsonToPojo(res, RetData.class);

        if (StatusEnums.ERROR.getStatusCode() == resData.getCode()) {
            throw new Exception(resData.getMsg());
        }
        JSONArray jArray = new JSONArray();
        if (resData.getResult().getItem_list() != null) {
            List list = (List) resData.getResult().getItem_list();
            for (Object obj : list) {
                jArray.add(obj);
            }
        }
        List<GenerateImportPO> list = JsonUtils.jsonToList(jArray.toString(), GenerateImportPO.class);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<GenerateImportPO>();
        }
        //生成TXT
        StringBuffer txtBuffer = new StringBuffer();
        String datedate = CCustomDate.replace(CCustomDate.getCurDate());
        //系统时间
        String field20 = CCustomDate.getCurDateTime_yyyyMMddHHmmss();
        for (int i = 0; i < list.size(); i++) {

            GenerateImportPO po = list.get(i);
            //記錄類型
            txtBuffer.append("D");
            //銀行代號
            txtBuffer.append("37");
            //聲明書編號(年份)
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField1(), 4));
            //聲明書編號(序號)
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField2(), 14));
            //聲明書編號(子序號)
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField3(), 2));
            //聲明書類型
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField4(), 1));
            //申請人之身份證明文件類型
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField5(), 3));
            //申請人之身份證明文件編號
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField6(), 20));
            //申請人之出生日期
            txtBuffer.append(YyStringUtils.addSpaceByRight(CCustomDate.replace(po.getField7()), 8));
            //申請人之性別
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField8(), 1));
            //申請人之銀行帳號
            txtBuffer.append(YyStringUtils.addZero(po.getField9(), 30));
            //申請人之本澳流動電話號碼
            txtBuffer.append(YyStringUtils.addZero(po.getField10(), 8));
            //申請人之電郵地址
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField11(), 60));
            //申請人是否同意接收中文訊息
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField12(), 1));
            //申請人是否同意接收葡文訊息
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField13(), 1));
            //申請人是否同意接收英文訊息
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField14(), 1));
            //聲明書申請日期
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField15(), 8));
            //聲明書申請時間
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField16(), 6));
            //財政局預留欄１
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField17(), 40));
            //財政局預留欄２
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField18(), 40));
            //銀行內部參考用欄
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField19(), 40));
            //銀行製作檔案之日期//銀行製作檔案之時間
//            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField20(), 14));
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField20(), 14));
            //申請人之葡文姓名
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField27(), 256));
            //申請人之中文姓名
            txtBuffer.append(YyStringUtils.addSpaceByRight(po.getField28(), 100));

            txtBuffer.append("\r\n");

            field20 =  po.getField20();
        }

        //最后一行汇总
        //記錄類型//銀行代號
        txtBuffer.append("C37");
        /*//銀行代號
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 2));*/
        //聲明書編號(年份)
        txtBuffer.append(YyStringUtils.addSpaceByRight("9999", 4));
        //聲明書編號(序號)
        txtBuffer.append(YyStringUtils.addSpaceByRight("99999999999999", 14));
        //聲明書編號(子序號)
        txtBuffer.append(YyStringUtils.addSpaceByRight("99", 2));
        //聲明書類型
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 1));
        //申請人之身份證明文件類型
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 3));
        //申請人之身份證明文件編號
        txtBuffer.append(YyStringUtils.addZero(list.size(), 20));
        //申請人之出生日期
        txtBuffer.append(YyStringUtils.addSpaceByRight("00000000", 8));
        //申請人之性別
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 1));
        //申請人之銀行帳號
        txtBuffer.append(YyStringUtils.addZero("999999999999999999999999999999", 30));
        //申請人之本澳流動電話號碼
        txtBuffer.append(YyStringUtils.addZero("00000000", 8));
        //申請人之電郵地址
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 60));
        //申請人是否同意接收中文訊息
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 1));
        //申請人是否同意接收葡文訊息
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 1));
        //申請人是否同意接收英文訊息
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 1));
        //聲明書申請日期
        txtBuffer.append(YyStringUtils.addSpaceByRight("99999999", 8));
        //聲明書申請時間
        txtBuffer.append(YyStringUtils.addSpaceByRight("999999", 6));
        //財政局預留欄１
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 40));
        //財政局預留欄２
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 40));
        //銀行內部參考用欄
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 40));
        //銀行製作檔案之日期//銀行製作檔案之時間
        txtBuffer.append(YyStringUtils.addSpaceByRight(field20, 14));
        //申請人之葡文姓名
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 256));
        //申請人之中文姓名
        txtBuffer.append(YyStringUtils.addSpaceByRight("", 100));

        String path = EXPORT_PATH_TEMP + "/" + UUID.randomUUID().toString() + "/";
        ExportDownLoad.generateTxtFileANSI(txtBuffer.toString(), path + "DAC37_" + datedate + "_1.TXT");
        //压缩ZIP
        String filename = "声明书.zip";
        FileUtil.fileToZip(path, path, filename);
        File zipFile = new File(path + filename);
        return FileUtil.fileToBytes(zipFile);

    }

}
