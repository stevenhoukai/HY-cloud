package com.yyicbc.controller;


import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.ModuleMap;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.querycondition.DailyReportQueryVO;
import com.yyicbc.beans.querycondition.LogQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/report/dailysummry")
public class DailyReportController_Consumer {

    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;


    @RequestMapping("/list")
    public String getAllDailyReport(@RequestParam Integer page,
                             @RequestParam String handleBeginDate,
                             @RequestParam String handleEndDate,
                             @RequestParam String corpcode
                             ) {
        DailyReportQueryVO vo = new DailyReportQueryVO();
        vo.setPage(page).setHandleBeginDate(handleBeginDate).setHandleEndDate(handleEndDate).setCorpCode(corpcode);
        String jsonStr = JsonUtils.objectToJson(vo);
        Map<String,String> map = new HashMap<String,String>();
        map.put("where",jsonStr);
        return restTemplate.getForObject(REST_URL_PREFIX + "/report/dailysummry/list?where={where}", String.class, map);
    }

//    @RequestMapping("/list")
//    public String getAllUser(@RequestParam LogQueryVO logQueryVO) {
//        String jsonStr = JsonUtils.objectToJson(logQueryVO);
//        Map<String,String> map = new HashMap<String,String>();
//        map.put("where",jsonStr);
//        return restTemplate.getForObject(REST_URL_PREFIX + "/system/log/list?where={where}", String.class, map);
//    }

    @PostMapping("/exportexcel")
    public byte[] exportExcel(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        OutputStream outputStream = null;
        HSSFWorkbook wb = null;
        HttpHeaders headers = new HttpHeaders();
//        DailyReportQueryVO vo = new DailyReportQueryVO();
//        Object obj = map.get("selectedIds");
        try {
//            vo.setHandleBeginDate(map.get("handleBeginDate").toString())
//                    .setHandleEndDate(map.get("handleEndDate").toString())
//                    .setCorpCode(map.get("corpcode").toString());
            Object obj = map.get("selectedIds");
            Map<String,String> requestmap = new HashMap<String,String>();
            String jsonStr = JsonUtils.objectToJson(obj);
            requestmap.put("dailyReportExportVO", jsonStr);
            HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(requestmap, headers);
            RetData retData = restTemplate.postForObject(REST_URL_PREFIX + "/report/dailysummry/exportexcel", req, RetData.class);
            List<LinkedHashMap> datalist = (List<LinkedHashMap>) retData.getResult().getItem_list();
            if(datalist==null||datalist.size()==0){
                throw new BusinessException(StatusCode.ERROR,"No data to export");
            }
            wb = generateEXCEL(datalist);
            response.setHeader("Content-Disposition", "attachment;Filename=" + System.currentTimeMillis() + "syslog.xls");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            outputStream = response.getOutputStream();
            wb.write(outputStream);
        } catch (BusinessException e) {
            throw new BusinessException(StatusCode.ERROR,"Export DailyReportExcel Error:{"+e.getMsg()+"}");
        } catch (Exception e){
            throw new BusinessException(StatusCode.ERROR,"Export DailyReportExcel Error:{"+e.getMessage()+"}");
        }finally {
            try {
                if(outputStream!=null){
                    outputStream.close();
                }
            } catch (IOException e) {
                throw new BusinessException(StatusCode.ERROR,"IOException:{"+e.getMessage()+"}");
            }
        }
        return wb.getBytes();
    }

    private HSSFWorkbook generateEXCEL(List<LinkedHashMap> datalist) throws IOException {
        HSSFWorkbook wb = null;
//        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"statics/excel/syslog.xls");
//        InputStream is = new FileInputStream(file);
        ClassPathResource resource = new ClassPathResource("statics/excel/dailyreport.xls");
        InputStream is = resource.getInputStream();
        wb = new HSSFWorkbook(is);
        HSSFSheet sheet = wb.getSheet("sheet1");
        sheet.setDefaultColumnWidth(20);// 默认列宽
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        HSSFRow row = null;
        int startRow = 2 ;
        //创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 添加excel title
        if(datalist!=null&&datalist.size()>0){
            for(int j = 0 ; j < datalist.size(); j++){
                LinkedHashMap map = datalist.get(j);
//                SysLogVO sysLogVO = datalist.get(j);
                row = sheet.createRow(startRow++);
                HSSFCell cell = null;
                for (int i = 0; i < 12; i++) {
                    cell = row.createCell((short) i);
                    switch(i){
                        case 0 :
                            cell.setCellValue(map.get("autopay_date")+"");
                            break; //可选
                        case 1 :
//                            Date handleTime = new Date(Long.parseLong(map.get("batch_no")+""));
//                            cell.setCellValue(format.format(handleTime));
                            cell.setCellValue(map.get("batch_no")+"");
                            break; //可选
                        case 2 :
                            cell.setCellValue(map.get("com_code")+"");
                            break; //可选
                        case 3 :
                            cell.setCellValue(map.get("icbc_ac_no")+"");
                            break; //可选
                        case 4 :
                            cell.setCellValue((map.get("txn_amt")==null? new Double(0): new Double(map.get("txn_amt")+"")));
                            break; //可选
                        case 5 :
                            cell.setCellValue((map.get("rec_cnt")==null? new Double(0): new Double(map.get("rec_cnt")+"")));
                            break; //可选
                        case 6 :
                            cell.setCellValue(map.get("txn_ccy")+"");
                            break; //可选
                        case 7 :
                            cell.setCellValue(map.get("direction")+"");
                            break; //可选
                        case 8 :
                            cell.setCellValue(map.get("drcr_flag")+"");
                            break; //可选
                        case 9 :
                            cell.setCellValue(map.get("maker")+"");
                            break; //可选
                        case 10 :
                            cell.setCellValue(map.get("checker")+"");
                            break; //可选
                        case 11 :
                            if((map.get("id").equals("")) && (map.get("updatestatus").equals("002"))){
                                cell.setCellValue("成功");
                            }else if((map.get("id").equals("")) && (!map.get("updatestatus").equals("002"))) {
                                cell.setCellValue("失败");
                            }
                            break; //可选
                        default : //可选
                            //语句
                    }
                }
            }
        }
        return wb;
    }

}
