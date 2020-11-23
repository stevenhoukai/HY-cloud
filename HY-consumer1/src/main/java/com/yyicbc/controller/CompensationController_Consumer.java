package com.yyicbc.controller;

import com.alibaba.fastjson.JSONObject;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.VO.CompensationVO;
import com.yyicbc.beans.enums.FileTypeEnums;
import com.yyicbc.beans.querycondition.CompensationQuestVO;
import com.yyicbc.beans.querycondition.UserImportQuestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/compensation")
@Slf4j
public class CompensationController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/add")
    public String add(@RequestBody CompensationVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompensationVO> req = new HttpEntity<CompensationVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/compensation/add", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("新增薪酬数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/unapprove")
    public String unapprove(@RequestBody CompensationVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompensationVO> req = new HttpEntity<>(request, headers);
        try {
            String response = restTemplate.postForObject(REST_URL_PREFIX + "/compensation/unapprove", req, String.class);
            return response;
        } catch (Exception e) {
            log.error("更新弃审状态异常 error={}" + e);
            return "";
        }
    }

    @GetMapping(value = "/getList")
    public String getList(@RequestParam(required = false, defaultValue = "1")  Integer page,
                           @RequestParam(required = false)  String companyNo,
                           @RequestParam(required = false)  String companyName,
                           @RequestParam(required = false)  String deductDateBegin,
                           @RequestParam(required = false)  String deductDateEnd
    ) {
        try {
            final Map<String, Object> param = new HashMap<>();
            param.put("page", page);
            param.put("companyNo", companyNo);
            param.put("companyName", companyName);
            param.put("deductDateBegin", deductDateBegin);
            param.put("deductDateEnd", deductDateEnd);
            String url = buildGetUrl(REST_URL_PREFIX, "/compensation/getList", param);
            return restTemplate.getForObject(fromHttpUrl(url), String.class);
        } catch (Exception e) {
            log.error("获取数据失败 error={}" + e);
            return "";
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody CompensationVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompensationVO> req = new HttpEntity<CompensationVO>(request, headers);
        try {
            return restTemplate.postForObject(REST_URL_PREFIX + "/compensation/update", req, String.class);
        } catch (Exception e) {
            log.error("更新薪酬数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/fovaUpdate")
    public byte[] fovaUpdate(@RequestBody UserImportQuestVO request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        request.setFileType(FileTypeEnums.FOVAFILE.getFileCode());
        HttpEntity<UserImportQuestVO> req = new HttpEntity<UserImportQuestVO>(request, headers);
        try {
            RetData Response = restTemplate.postForObject(REST_URL_PREFIX + "/compensation/fovaUpdate", req, RetData.class);
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

    @PostMapping("/updatedata")
    public String updatedata(@RequestParam String json) {
        CompensationVO vo = JSONObject.parseObject(json, CompensationVO.class);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompensationVO> req = new HttpEntity<CompensationVO>(vo, headers);
        try {
            return restTemplate.postForObject(REST_URL_PREFIX + "/compensation/update", req, String.class);
        } catch (Exception e) {
            log.error("更新薪酬数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/updatefrom")
    public String updatefrom(CompensationQuestVO vo) {
        if(!StringUtils.isEmpty(vo.getId()) && !StringUtils.isEmpty(vo.getDeductDate())){
            CompensationVO compensationVO = new CompensationVO();
            compensationVO.setId(vo.getId());
            //时间戳转换
            Long.valueOf(vo.getDeductDate());
            compensationVO.setDeductDate(new Date( Long.valueOf(vo.getDeductDate())));

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<CompensationVO> req = new HttpEntity<CompensationVO>(compensationVO, headers);
            try {
                return restTemplate.postForObject(REST_URL_PREFIX + "/compensation/update", req, String.class);
            } catch (Exception e) {
                log.error("更新薪酬数据异常 error={}" + e);
                return "";
            }
        }
        return "";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam(required = true)  Long id
    ) {
        try {
            final Map<String, Object> param = new HashMap<>();
            param.put("id", id);
            String url = buildGetUrl(REST_URL_PREFIX, "/compensation/delete", param);
            return restTemplate.getForObject(fromHttpUrl(url), String.class);
        } catch (Exception e) {
            log.error("删除数据失败 error={}" + e);
            return "";
        }
    }

    protected String buildGetUrl(final String baseUrl, final String action, Map<String, Object> param) {
        final StringBuffer url = new StringBuffer(baseUrl).append(action).append("?1=1");
        param.forEach((k, v) -> {
            if ( !StringUtils.isEmpty(v)) {
                url.append("&").append(k).append("=").append(v);
            }
        });
        return url.toString();
    }

    protected URI fromHttpUrl(String url){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        return builder.build().encode().toUri();
    }
}