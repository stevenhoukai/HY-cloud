package com.yyicbc.controller;

import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.business.VO.DealSymbolVO;
import com.yyicbc.beans.querycondition.DealSymbolQuestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/collection/dealSymbol")
@Slf4j
public class DealSymbolController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/add")
    public String add(DealSymbolVO request)
    {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<DealSymbolVO> req = new HttpEntity<DealSymbolVO>(request, headers);
        String response = "";
        try {
            response = restTemplate.postForObject(REST_URL_PREFIX + "/collection/dealSymbol/add", req, String.class);

        } catch (Exception e) {
            log.error("新增数据异常 error={}" + e);
            throw e;
        }
        return response;
    }

    @GetMapping("/lists")
    public String getList(@RequestParam(required = false, defaultValue = "1")  Integer page,
                           @RequestParam(required = false)  String symbolCode,
                           @RequestParam(required = false)  String symbolName
    ) {
        String response = "";
        try {
            final Map<String, Object> param = new HashMap<>();
            param.put("page", page);
            param.put("symbolCode", symbolCode);
            param.put("symbolName", symbolName);
            String url = buildGetUrl(REST_URL_PREFIX, "/collection/dealSymbol/lists", param);
            response = restTemplate.getForObject(fromHttpUrl(url), String.class);
        } catch (Exception e) {
            log.error("获取数据失败 error={}" + e);
            throw e;
        }
        return response;
    }

    @PostMapping("/update")
    public String update(DealSymbolVO vo)
    {

        if(StringUtils.isEmpty(vo.getId()) && StringUtils.isEmpty(vo.getSymbolCode())){
            RetData retData =  new RetData().setCode(StatusCode.ERROR).setMsg("标志编码不能为空");
            return  JsonUtils.objectToJson(retData);
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<DealSymbolVO> req = new HttpEntity<DealSymbolVO>(vo, headers);
        String response = "";
        try {
            response = restTemplate.postForObject(REST_URL_PREFIX + "/collection/dealSymbol/update", req, String.class);
        } catch (Exception e) {
            log.error("更新数据异常 error={}" + e);
            throw e;
        }
        return response;
    }

    @PostMapping("/delete")
    public String delete(DealSymbolQuestVO vo)
    {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<DealSymbolQuestVO> param = new HttpEntity<DealSymbolQuestVO>(vo, headers);
        String response = "";
        try {
            response = restTemplate.postForObject(REST_URL_PREFIX + "/collection/dealSymbol/delete",param, String.class);
        } catch (Exception e) {
            log.error("删除数据失败 error={}" + e);
            throw e;
        }
        return response;
    }

    private String buildGetUrl(final String baseUrl, final String action, Map<String, Object> param)
    {
        final StringBuffer url = new StringBuffer(baseUrl).append(action).append("?1=1");
        param.forEach((k, v) -> {
            if ( !StringUtils.isEmpty(v)) {
                url.append("&").append(k).append("=").append(v);
            }
        });
        return url.toString();
    }

    private URI fromHttpUrl(String url)
    {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        return builder.build().encode().toUri();
    }
}