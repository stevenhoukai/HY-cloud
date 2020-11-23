package com.yyicbc.controller;


import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.querycondition.ProtocalReportQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/report/subprotocal")
public class SubProtocalReportController_Consumer {

    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;


    @RequestMapping("/list")
    public String getAllProtocal(@RequestParam Integer page,
                             @RequestParam String handleBeginDate,
                             @RequestParam String handleEndDate
                             ) {
        ProtocalReportQueryVO vo = new ProtocalReportQueryVO();
        vo.setPage(page).setHandleBeginDate(handleBeginDate).setHandleEndDate(handleEndDate);
        String jsonStr = JsonUtils.objectToJson(vo);
        Map<String,String> map = new HashMap<String,String>();
        map.put("where",jsonStr);
        return restTemplate.getForObject(REST_URL_PREFIX + "/report/subprotocal/list?where={where}", String.class, map);
    }


    @PostMapping("/exportexcel")
    public byte[] exportExcel(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        OutputStream outputStream = null;
        HSSFWorkbook wb = null;
        HttpHeaders headers = new HttpHeaders();
        ProtocalReportQueryVO vo = new ProtocalReportQueryVO();
        try {
            vo.setHandleBeginDate(map.get("handleBeginDate").toString())
                    .setHandleEndDate(map.get("handleEndDate").toString());
            Map<String,String> requestmap = new HashMap<String,String>();
            String jsonStr = JsonUtils.objectToJson(vo);
            requestmap.put("subprotocalReportQueryVO", jsonStr);
            HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(requestmap, headers);
            RetData retData = restTemplate.postForObject(REST_URL_PREFIX + "/report/subprotocal/exportexcel", req, RetData.class);
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
            throw new BusinessException(StatusCode.ERROR,"Export SubProtocalExcel Error:{"+e.getMsg()+"}");
        } catch (Exception e){
            throw new BusinessException(StatusCode.ERROR,"Export SubProtocalExcel Error:{"+e.getMessage()+"}");
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
        ClassPathResource resource = new ClassPathResource("statics/excel/subprotocal.xls");
        InputStream is = resource.getInputStream();
        wb = new HSSFWorkbook(is);
        HSSFSheet sheet = wb.getSheet("sheet1");
        sheet.setDefaultColumnWidth(20);// 默认列宽
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        HSSFRow row = null;
        int startRow = 2 ;
        //创建单元格，并设置值表头 设置表头居中
//        HSSFCellStyle style = wb.createCellStyle();
        // 添加excel title
        if(datalist!=null&&datalist.size()>0){
            for(int j = 0 ; j < datalist.size(); j++){
                LinkedHashMap map = datalist.get(j);
//                SysLogVO sysLogVO = datalist.get(j);
                row = sheet.createRow(startRow++);
                HSSFCell cell = null;
                for (int i = 0; i <= 18; i++) {
                    cell = row.createCell((short) i);
                    switch(i){
                        case 0 :
                            cell.setCellValue(map.get("company_code")+"");
                            break; //可选
                        case 1 :
//                            Date handleTime = new Date(Long.parseLong(map.get("batch_no")+""));
//                            cell.setCellValue(format.format(handleTime));
                            cell.setCellValue(map.get("trade_code")+"");
                            break; //可选
                        case 2 :
                            cell.setCellValue(map.get("account_name")+"");
                            break; //可选
                        case 3 :
                            cell.setCellValue(map.get("fowa_account")+"");
                            break; //可选
                        case 4 :
                            cell.setCellValue(map.get("sub_agreement_number")+"");
                            break; //可选
                        case 5 :
                            cell.setCellValue(map.get("main_agreement_number")+"");
                            break; //可选
                        case 6 :
                            cell.setCellValue(map.get("ccy")+"");
                            break; //可选
                        case 7 :
                            cell.setCellValue(map.get("customer_status")+"");
                            break; //可选
                        case 8 :
                            cell.setCellValue(map.get("generate_date")+"");
                            break; //可选
                        case 9 :
                            cell.setCellValue(map.get("modify_time")+"");
                            break; //可选
                        case 10 :
                            cell.setCellValue(map.get("cancel_date")+"");
                            break; //可选
                        case 11 :
                            cell.setCellValue(map.get("report_time")+"");
                            break; //可选
                        case 12 :
                            cell.setCellValue(map.get("beneficiary_id")+"");
                            break; //可选
                        case 13 :
                            cell.setCellValue(map.get("reference_code")+"");
                            break; //可选
                        case 14 :
                            cell.setCellValue(map.get("reference_transaction")+"");
                            break; //可选
                        case 15 :
                            cell.setCellValue(map.get("bill_number")+"");
                            break; //可选
                        case 16 :
                            cell.setCellValue(map.get("comments")+"");
                            break; //可选
                        case 17 :
                            cell.setCellValue(map.get("product_index")+"");
                        case 18 :
                            cell.setCellValue(map.get("stop_amount_per")+"");
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
