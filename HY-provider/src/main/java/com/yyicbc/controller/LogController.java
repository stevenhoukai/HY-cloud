package com.yyicbc.controller;


import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.ModuleMap;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.querycondition.LogQueryVO;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.SysUserVO;
import com.yyicbc.service.UserService;
import com.yyicbc.service.impl.SysLogServiceImpl;
import com.yyicbc.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 以添加user示例通过consumer调用eureka注册中心
 *
 * @author ste
 * 用户管理节点controller
 */
@RestController
@RequestMapping(value = "/system/log", produces = {"application/json;charset=UTF-8"})
public class LogController {


    @Autowired
    SysLogServiceImpl sysLogServiceImpl;


    @GetMapping(value = "/list", name = "/system/log/list")
    public RetData all(@RequestParam Integer page,
                       @RequestParam Integer logModule,
                       @RequestParam Integer handleType,
                       @RequestParam String handleUser,
                       @RequestParam String handleBeginDate,
                       @RequestParam String handleEndDate) {
        LogQueryVO vo = new LogQueryVO();
        vo.setPage(page).setLogModule(logModule).setHandleType(handleType).setHandleUser(handleUser).
                setHandleBeginDate(handleBeginDate).setHandleEndDate(handleEndDate);
        final RetData result = sysLogServiceImpl.all(vo);
        return result;
    }




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
            RetData retData = sysLogServiceImpl.exportExcel(vo);
            List<LinkedHashMap> datalist = (List<LinkedHashMap>) retData.getResult().getItem_list();
            if(datalist==null||datalist.size()==0){
                throw new com.yyicbc.beans.Exception.BusinessException(StatusCode.ERROR,"No data to export");
            }
            wb = generateEXCEL(datalist);
            response.setHeader("Content-Disposition", "attachment;Filename=" + System.currentTimeMillis() + "syslog.xls");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            outputStream = response.getOutputStream();
            wb.write(outputStream);
        } catch (com.yyicbc.beans.Exception.BusinessException e) {
            throw new com.yyicbc.beans.Exception.BusinessException(StatusCode.ERROR,"Export LogExcel Error:{"+e.getMsg()+"}");
        } catch (Exception e){
            throw new com.yyicbc.beans.Exception.BusinessException(StatusCode.ERROR,"Export LogExcel Error:{"+e.getMessage()+"}");
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