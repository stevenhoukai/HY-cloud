package com.yyicbc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.business.DTO.Ai63015Request;
import com.yyicbc.beans.business.DTO.Ai63015Response;
import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.business.VO.CompanyAgreementVO;
import com.yyicbc.beans.business.VO.CustomerAgreementVO;
import com.yyicbc.beans.enums.StatusEnums;
import com.yyicbc.beans.querycondition.CompanyQuestVO;
import com.yyicbc.beans.querycondition.UserImportQuestVO;
import com.yyicbc.beans.utils.AmountUtil;
import com.yyicbc.utils.ExportNewApplyDownLoad;
import com.yyicbc.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection/customerAgreementFile")
@Slf4j
public class CustomerAgreementController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;
    @Value("${icbc.base}")
    private String base;
    @Value("${icbc.ai63015}")
    private String ai63015;
    @Autowired
    RestTemplate restTemplate;
    @Resource
    private ExportNewApplyDownLoad exportNewApplyDownLoad;

    @RequestMapping("/getList")
    public String getList(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/customerAgreementFile/getList", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取公司协议档案列表页数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/add")
    public String add(CustomerAgreementVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CustomerAgreementVO> req = new HttpEntity<CustomerAgreementVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/customerAgreementFile/add", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("新增公司协议档案数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/getCompanyCode")
    public String getCompanyCode(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/customerAgreementFile/getCompanyCode", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("获取公司代码异常 error={}" + e);
            return "";
        }
    }

    @GetMapping("/getCompanyEncode")
    public String getCompanyEncode(CompanyQuestVO request) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("companyCode", request.getCompanyCode());
        try {
            String requestUrl = buildGetUrl(REST_URL_PREFIX, "/collection/customerAgreementFile/getCompanyEncode", paramMap);
            return restTemplate.getForObject(fromHttpUrl(requestUrl), String.class);
        } catch (Exception e) {
            log.error("获取公司编码失败 error={}" + e);
            return "";
        }

    }

    protected String buildGetUrl(final String baseUrl, final String action, Map<String, Object> param) {
        final StringBuffer url = new StringBuffer(baseUrl).append(action).append("?1=1");
        param.forEach((k, v) -> {
            if (!StringUtils.isEmpty(v)) {
                url.append("&").append(k).append("=").append(v);
            }
        });
        return url.toString();
    }

    protected URI fromHttpUrl(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        return builder.build().encode().toUri();
    }

    @PostMapping("/updateStatus")
    public String updateStatus(CustomerAgreementVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CustomerAgreementVO> req = new HttpEntity<CustomerAgreementVO>(request, headers);
        try {
            String updateResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/customerAgreementFile/updateStatus", req, String.class);
            return updateResponse;
        } catch (Exception e) {
            log.error("更新公司协议档案数据异常 error={}" + e);
            return "";
        }
    }

    @PostMapping("/update")
    public String update(CustomerAgreementVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CustomerAgreementVO> req = new HttpEntity<CustomerAgreementVO>(request, headers);
        try {
            String updateResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/customerAgreementFile/update", req, String.class);
            return updateResponse;
        } catch (Exception e) {
            log.error("更新公司协议档案数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/delete")
    public String delete(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/customerAgreementFile/delete", req, String.class);
            return deleteResponse;
        } catch (Exception e) {
            log.error("删除公司档协议案数据异常 error={}" + e);
            return "";
        }
    }

    @RequestMapping("/generate")
    public RetData generateSubCode(CustomerAgreementVO request) {

        Ai63015Request ai63015Request = buildParam(request);

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            map.add("xmlai63015", xmlMapper.writeValueAsString(ai63015Request));

            log.info("子协议【"+request.getType().toUpperCase()+"】 ai3015 调用入参 param=" + xmlMapper.writeValueAsString(ai63015Request));

            RestTemplate rest = new RestTemplate();

            HttpEntity<MultiValueMap<String, String>> remoteRequest = new HttpEntity<MultiValueMap<String, String>>(map, httpHeaders);
            Ai63015Response ai63015Response = rest.postForObject(base + ai63015, remoteRequest, Ai63015Response.class);

            log.info("子协议【"+request.getType().toUpperCase()+"】 调用 ai3015 响应 result=" + xmlMapper.writeValueAsString(ai63015Response));

            if ("0".equals(ai63015Response.getTRANSOK())) { //正常
                if("cancel".equals(request.getType())){//取消就地执行数据更新
                    HttpHeaders headers = new HttpHeaders();
                    CustomerAgreementVO cVO = new CustomerAgreementVO();
                    cVO.setId(request.getId());
                    cVO.setCustomerStatus("1");
                    HttpEntity<CustomerAgreementVO> req = new HttpEntity<>(cVO, headers);

                    String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX+"/collection/customerAgreementFile/updateCustomerStatus", req, String.class);
                    Map<String, String> mapRsp = JSONObject.parseObject(deleteResponse, Map.class);
                    if(new Integer(20000).equals(mapRsp.get("code"))){
                        RetData retCancel = RetData.build(StatusEnums.SUCCESS.getStatusCode(), "取消协议成功");
                        return retCancel;
                    }
                    RetData retCancel = RetData.build(StatusEnums.SUCCESS.getStatusCode(), "取消协议成功，但状态更新失败");
                    return retCancel;
                }
                request.setSubAgreementNumber(ai63015Response.getPROTSENO()); // 子协议

                RetResult retResult = new RetResult().setItem_list(ai63015Response.getPROTSENO());

                return new RetData(StatusEnums.SUCCESS, retResult);
            } else {
                log.error("ai63015 接口调用出错 RESPONSE_ERROR_CODE=" + ai63015Response.getERR_NO());

                return RetData.build(ai63015Response.getERR_NO(), "接口调用出错：" + ai63015Response.getERR_NO());
            }

        } catch (Exception e) {
            log.error("调用 ai63015 接口异常 exception=" + e.getMessage());
            RetData retData = RetData.build(StatusEnums.ERROR.getStatusCode(), "调用ai63015接口异常 exception=" + e.getMessage());
            return retData;
        }
    }

    private Ai63015Request buildParam(CustomerAgreementVO caVO) {
        Ai63015Request ai63015Request = new Ai63015Request();
        //公共必需参数
        ai63015Request.setPROTFLG(caVO.getSignDriect());//签约方向标志 1发起方；2从属方
        ai63015Request.setPRODSENO(caVO.getProductIndex());//产品序号
        ai63015Request.setFEESENO(caVO.getProductIndex());//费用序号与产品序号一致
        ai63015Request.setMEDIUMID(caVO.getFowaAccount());//介质识别号
        ai63015Request.setAGTSNAME(caVO.getProductTypeShort());//业务种类简称
        ai63015Request.setPRODTYPE(caVO.getProductType());//产品种类
        ai63015Request.setFEEFLAG(caVO.getChargeSign());//收费标志
        ai63015Request.setCUSTNO(caVO.getCustomerIndex());//客户编码
        ai63015Request.setSIGNCURR("" + Integer.parseInt(caVO.getCcy()));//币种
        ai63015Request.setSTPCUR(caVO.getStopCCY());//止付币种
        ai63015Request.setSIGNCAEF(caVO.getPaySign());//抄汇标志
        ai63015Request.setMEDTYPE("20101");//介质种类：30101-网银；30102-电话银行 20101 活期存折
        ai63015Request.setINPROTF("1");//内部协议标志: 0 否，1是
        ai63015Request.setMEDIDTP("0");//介质类型: 0.接口自行判断 1.卡 2.帐户协议 3.内部户帐号 4.表外户 5．虚拟介质
        ai63015Request.setRESERFLG(caVO.getCcy());//计费币种（收费标志为1时不输）  人民币： 001、 港币：013、澳币：081
        ai63015Request.setCORPFLAG(caVO.getCompanySign());//企业签名标志: 0没有签名；1有签名
        ai63015Request.setTRNCODE(caVO.getTradeCode());//交易代码
        ai63015Request.setDRCRF(caVO.getBorrowSign());//借贷标志
        ai63015Request.setNOTES(caVO.getComments());//备注
        ai63015Request.setSUMMARY(caVO.getAbstractContent());//摘要

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ai63015Request.setCTDATE(caVO.getGenerateDate());//创建日期
        ai63015Request.setACTVDATE(caVO.getGenerateDate());//生效日期

        //选填
        if(!StringUtils.isEmpty(caVO.getMaxAmount())){
            ai63015Request.setFIELD1(Math.round(100 * AmountUtil.formatToNumber(new BigDecimal(caVO.getMaxAmount())))+"");//最高金额
        }
        if(!StringUtils.isEmpty(caVO.getMinAmount())){
            ai63015Request.setFIELD2(Math.round(100 * AmountUtil.formatToNumber(new BigDecimal(caVO.getMinAmount()))) + "");//最低金额
        }
        if(!StringUtils.isEmpty(caVO.getStopAmountPer())){
            ai63015Request.setSTPAMT(Math.round(100 * AmountUtil.formatToNumber(new BigDecimal(caVO.getStopAmountPer()))) + "");//单次止付金额
        }
        if(!StringUtils.isEmpty(caVO.getStopAmountMonth())){
            ai63015Request.setSTTAMT(Math.round(100 * AmountUtil.formatToNumber(new BigDecimal(caVO.getStopAmountMonth()))) + "");//每月止付金额
        }

//        if (!"1".equals(caVO.getChargeSign())) { // 收费标志为1时不输
        if(!StringUtils.isEmpty(caVO.getChargeByNumberStandard())) {
            ai63015Request.setFEEAMT(Math.round(100 * AmountUtil.formatToNumber(new BigDecimal(caVO.getChargeByNumberStandard()))) + "");//费用金额
        }
        if(!StringUtils.isEmpty(caVO.getScalepc())) {
            ai63015Request.setSCALEPC(Math.round(100 * AmountUtil.formatToNumber(new BigDecimal(caVO.getScalepc()))) + "");//按金额收费的比例（收费标志为1时不输）
        }

        ai63015Request.setFEEMETHD(caVO.getChargeMethod());//收费方式（收费标志为1时不输）
//        }


        if (StringUtils.isEmpty(caVO.getSubAgreementNumber())) {
            //动作标识：2. 修改 3. 刪除 4. 新增
            ai63015Request.setOPERFLAG("4");//新增
            ai63015Request.setMAINPTSN(caVO.getMainAgreementNumber());//代理业务主协议号
            ai63015Request.setPROTSENO(caVO.getMainAgreementNumber());//协议编号
        } else {
            switch(caVO.getType()){
                case "edit":
                case "updateService":
                    ai63015Request.setMAINPTSN(caVO.getMainAgreementNumber());//代理业务主协议号
                    ai63015Request.setPROTSENO(caVO.getSubAgreementNumber());//修改必传，协议编号
                    ai63015Request.setOPERFLAG("2");//修改
                    break;
                case "cancel":
                    ai63015Request = new Ai63015Request();
                    ai63015Request.setMAINPTSN(caVO.getMainAgreementNumber());//终止必输
                    ai63015Request.setPROTSENO(caVO.getSubAgreementNumber());//修改必传，协议编号
                    ai63015Request.setCUSTNO(caVO.getCustomerIndex());//客户编码
                    ai63015Request.setOPERFLAG("3");
                    break;
            }
            ai63015Request.setLTDATE(sdf.format(new Date()));//上次修改日期
        }
        return ai63015Request;
    }

    @GetMapping("/getMainAgreementByCompanyCode")
    public String getMainAgreementByCompanyCode(@RequestParam String companyCode) {
        UserImportQuestVO userImportQuestVO = new UserImportQuestVO();
        userImportQuestVO.setCompanyCode(companyCode);
        try {
            String companyAgreement = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/getCompanyAgreementPOBycode", userImportQuestVO, String.class);
            return companyAgreement;
        } catch (Exception e) {
            return "未找到主协议信息";
        }
    }

    @GetMapping("/generateReferenceTransactionCode")
    public String generateReferenceTransactionCode(@RequestParam String companyCode, @RequestParam String referenceTransaction) {
        UserImportQuestVO userImportQuestVO = new UserImportQuestVO();
        userImportQuestVO.setCompanyCode(companyCode);
        try {
            String url = String.format(REST_URL_PREFIX + "/collection/customerAgreementFile/generateReferenceTransactionCode?companyCode=%s&referenceTransaction=%s", companyCode, referenceTransaction);
            String companyAgreement = restTemplate.getForObject(url, String.class);
            return companyAgreement;
        } catch (Exception e) {
            log.error("生成参考交易号 error={}" + e.getMessage());

            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    @RequestMapping("/searchAccount")
    public String searchAccount(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            return restTemplate.postForObject(REST_URL_PREFIX + "/collection/customerAgreementFile/searchAccount", req, String.class);
        } catch (Exception e) {
            log.error("查询账户名数据异常 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    @RequestMapping("/generateCompanyReportDate")
    public String generateCompanyReportDate(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            return restTemplate.postForObject(REST_URL_PREFIX + "/collection/customerAgreementFile/generateCompanyReportDate", req, String.class);
        } catch (Exception e) {
            log.error("生成报表日期异常 error={}" + e.getMessage());
            return RetData.build(StatusEnums.REMOTE_ERROR.getStatusCode(), "生成报表日期异常 error={}").toString();
        }
    }


    /**
     * 功能描述: 导出新申请TXT/PDF
     *
     * @param request
     * @return: {@link byte[]}
     * @Author: vic
     * @Date: 2020/3/25 10:32
     */
    @RequestMapping("/exportNewApplyFile")
    public byte[] exportNewApplyFile(@RequestBody CompanyQuestVO request) throws Exception {
        //获取结果数据
        String data = restTemplate.postForObject(REST_URL_PREFIX + "/collection/customerAgreementFile/getNewApplyDataByDate", request, String.class);
        List<CusetomerAgreementPO> list = JSONArray.parseArray(data).toJavaList(CusetomerAgreementPO.class);
        File output = exportNewApplyDownLoad.exportFile(list, request);
        return FileUtil.fileToBytes(output);

    }
}