package com.yyicbc.controller;


import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.ModuleMap;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.business.VO.ExcelHeaderVO;
import com.yyicbc.beans.enums.FileTypeEnums;
import com.yyicbc.beans.logmanager.SysLogVO;
import com.yyicbc.beans.querycondition.LogQueryVO;
import com.yyicbc.beans.querycondition.UserImportQuestVO;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.SysUserVO;
import com.yyicbc.beans.utils.distribute.method.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/system/log")
public class LogController_Consumer {

    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;


    @RequestMapping("/list")
    public String getAllUser(@RequestParam Integer page,
                             @RequestParam Integer logModule,
                             @RequestParam Integer handleType,
                             @RequestParam String handleUser,
                             @RequestParam String handleBeginDate,
                             @RequestParam String handleEndDate
                             ) {
        LogQueryVO vo = new LogQueryVO();
        vo.setPage(page).setLogModule(logModule).setHandleType(handleType).setHandleUser(handleUser).
                setHandleBeginDate(handleBeginDate).setHandleEndDate(handleEndDate);
        String jsonStr = JsonUtils.objectToJson(vo);
        Map<String,String> map = new HashMap<String,String>();
        map.put("where",jsonStr);
        return restTemplate.getForObject(REST_URL_PREFIX + "/system/log/list?where={where}", String.class, map);
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
        LogQueryVO vo = new LogQueryVO();
        try {
            vo.setLogModule(StringUtils.isBlank(map.get("logModule").toString())?null:Integer.parseInt(map.get("logModule").toString()))
                    .setHandleType(StringUtils.isBlank(map.get("handleType").toString())?null:Integer.parseInt(map.get("handleType").toString()))
                    .setHandleUser(map.get("handleUser").toString())
                    .setHandleBeginDate(map.get("handleBeginDate").toString())
                    .setHandleEndDate(map.get("handleEndDate").toString());
            Map<String,String> requestmap = new HashMap<String,String>();
            String jsonStr = JsonUtils.objectToJson(vo);
            requestmap.put("logQueryVO", jsonStr);
            HttpEntity<Map<String, String>> req = new HttpEntity<Map<String, String>>(requestmap, headers);
            RetData retData = restTemplate.postForObject(REST_URL_PREFIX + "/system/log/exportexcel", req, RetData.class);
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
            throw new BusinessException(StatusCode.ERROR,"Export LogExcel Error:{"+e.getMsg()+"}");
        } catch (Exception e){
            throw new BusinessException(StatusCode.ERROR,"Export LogExcel Error:{"+e.getMessage()+"}");
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
        ClassPathResource resource = new ClassPathResource("statics/excel/syslog.xls");
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
                for (int i = 0; i < 8; i++) {
                    cell = row.createCell((short) i);

                    switch(i){
                        case 0 :
                            cell.setCellValue(map.get("logId")+"");
                            break; //可选
                        case 1 :
                            Date handleTime = new Date(Long.parseLong(map.get("handleTime")+""));
                            cell.setCellValue(format.format(handleTime));
                            break; //可选
                        case 2 :
                            cell.setCellValue(ModuleMap.modulesShowMap.get(map.get("moduleName")+""));
                            break; //可选
                        case 3 :
                            cell.setCellValue(map.get("handleUsercode")+"");
                            break; //可选
                        case 4 :
                            cell.setCellValue(map.get("handleUsername")+"");
                            break; //可选
                        case 5 :
                            cell.setCellValue(ModuleMap.typeShowMap.get(map.get("handleType")+""));
                            break; //可选
                        case 6 :
                            cell.setCellValue(map.get("handleObject")+"");
                            break; //可选
                        case 7 :
                            cell.setCellValue(map.get("handleMemo")+"");
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
