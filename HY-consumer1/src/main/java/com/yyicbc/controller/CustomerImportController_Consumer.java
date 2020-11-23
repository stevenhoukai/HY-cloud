package com.yyicbc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.PO.CompanyAgreementPO;
import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.business.VO.CompanyAccountVO;
import com.yyicbc.beans.business.VO.ExcelHeaderVO;
import com.yyicbc.beans.business.VO.TxtHeaderVO;
import com.yyicbc.beans.enums.FileTypeEnums;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.querycondition.UserImportQuestVO;
import com.yyicbc.beans.utils.distribute.method.MapUtil;
import com.yyicbc.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/imports/userImport")
@Slf4j
public class CustomerImportController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @Resource
    private PDFtemplateForDownLoad pdFtemplateForDownLoad;
    @Resource
    private TXTtemplateForDownload txtTemplateForDownload;
    @Resource
    private ExportDownLoad exportDownLoad;

    @RequestMapping("/getList")
    public String getList(UserImportQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/getList", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取导入流水数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/delete")
    public String delete(UserImportQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/delete", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("删除导入流水数据异常 error={}" + e.getMessage());
            return "";
        }
    }

    /**功能描述:DSF 补号
     * @param request
    * @return: {@link String}
    * @Author: vic
    * @Date: 2020/3/26 10:42
    */
    @RequestMapping("/addDsfAccno")
    public String addDsfAccno(UserImportQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            String res = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/addDsfAccno", req, String.class);
            return res;
        } catch (Exception e) {
            log.error("补号失败数据异常 error={}" + e.getMessage());
            return RetData.resFail(e.getMessage()).toString();
        }
    }

    @RequestMapping("/getCompanyAccount")
    public String getCompanyAccount(CompanyAccountVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyAccountVO> req = new HttpEntity<>(request, headers);

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("businessTypeCode", request.getBusinessTypeCode());
        paramsMap.put("ccy", request.getCcy());
        try {
            return restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/getCompanyAccount", req, String.class, paramsMap);
        } catch (Exception e) {
            log.error("获取公司账户异常 error={}" + e.getMessage());
            return "";
        }
    }

    @RequestMapping("/getCompanyCode")
    public String getCompanyCode(UserImportQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/getCompanyCode", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取公司代码异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/review")
    public String review(UserImportQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/review", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("复审导入流水数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/abandon")
    public String abandon(UserImportQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            String response = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/abandon", req, String.class);
            return response;
        } catch (Exception e) {
            log.error("弃审失败 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/detail")
    public String detail(UserImportQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/detail", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取明细数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/updateDetail")
    public String updateDetail(UserImportQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/updateDetail", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("更新明细数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/downLoadOrigion")
    public byte[] downLoadOrigion(@RequestBody UserImportQuestVO request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            byte[] resp = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/downLoadOrigion", req, byte[].class);
            response.setHeader("Content-Disposition", "attachment;Filename=" + System.currentTimeMillis());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(resp);
            outputStream.close();
            return resp;
        } catch (Exception e) {
            log.error("下载上传的文件异常 error={}" + e);
            return null;
        }
    }

    @PostMapping("/exportTXT")
    public String exportTXT(@RequestBody UserImportQuestVO request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        request.setFileType(FileTypeEnums.TXT.getFileCode());
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            RetData Response = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/getHeader", req, RetData.class);
            if (Response.getResult() != null) {
                LinkedHashMap tmp = (LinkedHashMap) Response.getResult().getItem_list();
                List<LinkedHashMap> head = (List<LinkedHashMap>) tmp.get("title");
                List<LinkedHashMap> column = (List<LinkedHashMap>) tmp.get("column");
                List<TxtHeaderVO> headVOList = convertLinkedListToObiList(head);
                List<TxtHeaderVO> columnVOList = convertLinkedListToObiList(column);
                String str = generateTXT(headVOList, columnVOList);
                return str;
            }
        } catch (Exception e) {
            log.error("导出TXT异常 error={}" + e);
        }
        return null;
    }

    @PostMapping("/fovaUpdate")
    public byte[] fovaUpdate(@RequestBody UserImportQuestVO request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        request.setFileType(FileTypeEnums.FOVAFILE.getFileCode());
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            RetData Response = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/fovaUpdate", req, RetData.class);
            File file = new File((String) Response.getResult().getItem_list());
            FileInputStream fis = new FileInputStream(file);
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
                byte[] bytes = new byte[1024];
                int temp;
                while ((temp = fis.read(bytes)) != -1) {
                    baos.write(bytes, 0, temp);
                }
                fis.close();
                baos.close();
                byte[] buffer = baos.toByteArray();
                return buffer;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            log.error("导出Fova数据异常 error={}" + e);
        }
        return null;
    }

    @RequestMapping("/exportEXCEL")
    public byte[] exportEXCEL(@RequestBody UserImportQuestVO request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        request.setFileType(FileTypeEnums.EXCEL.getFileCode());
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            RetData responseHeadList = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/getHeader", req, RetData.class);
            List<ExcelHeaderVO> generateList = new ArrayList<ExcelHeaderVO>();
            //将map对象转换成List
            if (responseHeadList.getResult() != null) {
                List<LinkedHashMap> tmp = (List<LinkedHashMap>) responseHeadList.getResult().getItem_list();
                for (LinkedHashMap item : tmp) {
                    ExcelHeaderVO head = new ExcelHeaderVO();
                    MapUtil.copyValue(item, head);
                    generateList.add(head);
                }
            }
            HSSFWorkbook wb = generateEXCEL(generateList);
            response.setHeader("Content-Disposition", "attachment;Filename=" + System.currentTimeMillis() + ".xls");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            OutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.close();
            return wb.getBytes();
        } catch (Exception e) {
            log.error("导出EXCEL异常 error={}" + e);
            return null;
        }
    }

    @RequestMapping("/importFile")
    public String importFile(UserImportQuestVO request) {
        try {
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            MultipartFile file = request.getFile();
            InputStream ins = file.getInputStream();
            File toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
            FileSystemResource resource = new FileSystemResource(toFile);
            param.add("file", resource);
            param.add("fileType", request.getFileType());
            param.add("makerId", request.getMakerId());
            param.add("companyEncode", request.getCompanyEncode());
            param.add("companyCode", request.getCompanyCode());
            param.add("fileName", request.getFile().getOriginalFilename());
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<MultiValueMap<String, Object>> req = new HttpEntity<MultiValueMap<String, Object>>(param, headers);
            String Response = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/importFile", req, String.class);
            toFile.delete();
            return Response;
        } catch (Exception e) {
            log.error("导入文件异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/fovaReturnFile")
    public String fovaReturnFile(UserImportQuestVO request) {
        try {
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            MultipartFile file = request.getFile();
            InputStream ins = file.getInputStream();
            File toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
            FileSystemResource resource = new FileSystemResource(toFile);
            param.add("file", resource);
            param.add("companyEncode", request.getCompanyEncode());
            param.add("fileName", request.getFile().getOriginalFilename());
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<MultiValueMap<String, Object>> req = new HttpEntity<MultiValueMap<String, Object>>(param, headers);
            String Response = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/fovaReturnFile", req, String.class);
            toFile.delete();
            return Response;
        } catch (Exception e) {
            log.error("上传fova返回文件异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/downLoadResultData")
    public byte[] downLoadResultData(@RequestBody UserImportQuestVO request, HttpServletResponse response) throws Exception {

        //获取结果数据
        String data = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/getDataByflowId", request, String.class);
        List<FovaUpdateDataPO> generateList = JSONArray.parseArray(data).toJavaList(FovaUpdateDataPO.class);
        //获取模板信息
        String company = restTemplate.postForObject(REST_URL_PREFIX + "/imports/userImport/getCompanyTemplate", request, String.class);
        CompanyBasePO companyDetail = JSONObject.parseObject(company, CompanyBasePO.class);
        //add by fmm 根據公司代碼獲取协议档案
        String companyAgreement = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/getCompanyAgreementPOBycode", request, String.class);
        CompanyAgreementPO companyAgreementPO = JSONObject.parseObject(companyAgreement, CompanyAgreementPO.class);
        File output = exportDownLoad.exportFile(generateList, companyDetail, companyAgreementPO, request.getFileType());
        return FileUtil.fileToBytes(output);


        /*//设置Http的Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        //设置访问参数
        HashMap<String, Object> params = new HashMap<>();
        params.put("flowId", request.getFlowId());
        params.put("companyCode", request.getCompanyCode());
        params.put("fileType", request.getFileType());
        params.put("companyEncode", request.getCompanyEncode());

        HttpEntity httpEntity = new HttpEntity<>(params,headers);
        ResponseEntity<byte[]> result = restTemplate.exchange(REST_URL_PREFIX + "/imports/userImport/downLoadResultData",
                HttpMethod.POST, httpEntity, byte[].class);
        byte[] bytes = result.getBody();
*/

        //modify by vic
       /* if (request.getFileType() == FileTypeEnums.TXT.getFileCode()) {
            return generateTXTWithData(generateList, companyDetail);
        }
        if (request.getFileType() == FileTypeEnums.EXCEL.getFileCode()) {
            return generateEXCELWithData(generateList, companyDetail.getExportExcelTemplateType());
        }
        if (request.getFileType() == FileTypeEnums.PDF.getFileCode()) {

            return generatePDFWithData(generateList, companyDetail,companyAgreementPO);
        }
        return bytes;*/
    }

    private byte[] generateTXTWithData(List<FovaUpdateDataPO> detailData, CompanyBasePO companyDetail) throws Exception {
        //modify by fmm
        return txtTemplateForDownload.getTxt2(detailData, companyDetail);
        /*try {
            File output =  txtTemplateForDownload.getTxt2(templateCode, detailData);
            return FileUtil.fileToBytes(output);
        } catch (Exception e) {
            log.error("生成TXT文件失败");
            e.printStackTrace();
        }
        return null;*/
    }

    private byte[] generateEXCELWithData(List<FovaUpdateDataPO> detailData, String templateCode) {
        return EXCELtemplateForDownload.getExcel(templateCode, detailData);
    }

    /**
     * add by fmm生成PDF
     *
     * @param detailList
     * @param companyDetail
     * @param companyAgreementPO
     * @return
     */
    private byte[] generatePDFWithData(List<FovaUpdateDataPO> detailList, CompanyBasePO companyDetail, CompanyAgreementPO companyAgreementPO) {
        try {
            File output = pdFtemplateForDownLoad.getPdfFile(detailList, companyDetail, companyAgreementPO);
            return FileUtil.fileToBytes(output);
        } catch (Exception e) {
            log.error("生成pdf文件失败");
            e.printStackTrace();
        }
        return null;
    }

    /*private byte[] generatePDFWithData(List<FovaUpdateDataPO> detailList, String templateCode) {
        try {
            Document document = new Document(new Rectangle(PageSize.A4));
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("\\output.pdf"));
            writer.flush();
            writer.close();
            PDFtemplateForDownLoad.getDocument(templateCode, detailList, document);
            File output = new File("\\output.pdf");
            byte[] bFile = new byte[(int) output.length()];
            FileInputStream fis = new FileInputStream(output);
            fis.read(bFile);
            fis.close();
            output.delete();
            return bFile;
        } catch (Exception e) {
            log.error("生成pdf文件失败");
        }
        return null;
    }*/


    private List<TxtHeaderVO> convertLinkedListToObiList(List<LinkedHashMap> input) {
        List<TxtHeaderVO> generateList = new ArrayList<TxtHeaderVO>();
        for (LinkedHashMap item : input) {
            TxtHeaderVO head = new TxtHeaderVO();
            MapUtil.copyValue(item, head);
            generateList.add(head);
        }
        return generateList;
    }

    private String generateTXT(List<TxtHeaderVO> title, List<TxtHeaderVO> column) {
        StringBuffer respString = new StringBuffer();
        //表头串生成
        for (int i = 0; i < title.size(); i++) {
            for (TxtHeaderVO item : title) {
                if (Integer.parseInt(item.getRowNumber()) == i + 1) {
                    respString.append(item.getHeaderName() + ":");
                    respString.append("       ");
                }
            }
            respString.append("\r\n");
        }
        //列表串生成
        for (TxtHeaderVO item : column) {
            respString.append(item.getHeaderName() + "    ");
        }
        return respString.toString();
    }

    private HSSFWorkbook generateEXCEL(List<ExcelHeaderVO> head) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        sheet.setDefaultColumnWidth(20);// 默认列宽
        HSSFRow row = sheet.createRow((int) 0);
        //创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 添加excel title
        HSSFCell cell = null;
        for (int i = 0; i < head.size(); i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(head.get(i).getHeaderName());
            cell.setCellStyle(style);
        }
        return wb;
    }

    private void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}