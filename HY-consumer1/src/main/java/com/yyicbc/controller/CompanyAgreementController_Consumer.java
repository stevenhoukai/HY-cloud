package com.yyicbc.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.business.DTO.Ai63015Request;
import com.yyicbc.beans.business.DTO.Ai63015Response;
import com.yyicbc.beans.business.VO.CompanyAgreementVO;
import com.yyicbc.beans.enums.StatusEnums;
import com.yyicbc.beans.utils.AmountUtil;
import com.yyicbc.beans.querycondition.CompanyQuestVO;
import com.yyicbc.beans.utils.distribute.method.StringFormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/collection/companyAgreementFile")
@Slf4j
public class CompanyAgreementController_Consumer {
    @Value("${rest.location.prefix}")
    private String REST_URL_PREFIX;
    @Value("${icbc.base}")
    private String base;
    @Value("${icbc.ai63015}")
    private String ai63015;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/getList")
    public String getList(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/getList", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取公司协议档案列表页数据异常 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    @RequestMapping("/getCompanyAccount")
    public String getCompanyAccount(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/getCompanyAccount", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取公司协议档案表单页公司账号异常 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    @RequestMapping("/getBusinessType")
    public String getBusinessType() {
        HttpHeaders headers = new HttpHeaders();
        CompanyQuestVO request = new CompanyQuestVO();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/getBusinessType", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取业务种类异常 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    @RequestMapping("/getCompanyFile")
    public String getCompanyFile() {
        HttpHeaders headers = new HttpHeaders();
        CompanyQuestVO request = new CompanyQuestVO();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/getCompanyFile", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取业务种类异常 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    @RequestMapping("/getCCYType")
    public String getCCYType() {
        HttpHeaders headers = new HttpHeaders();
        CompanyQuestVO request = new CompanyQuestVO();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String ListResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/getCCYType", req, String.class);
            return ListResponse;
        } catch (Exception e) {
            log.error("获取业务种类异常 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    @PostMapping("/add")
    public String add(CompanyAgreementVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyAgreementVO> req = new HttpEntity<CompanyAgreementVO>(request, headers);
        try {
            String addResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/add", req, String.class);
            return addResponse;
        } catch (Exception e) {
            log.error("新增公司协议档案数据异常 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    @PostMapping("/update")
    public String update(CompanyAgreementVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyAgreementVO> req = new HttpEntity<CompanyAgreementVO>(request, headers);
        try {
            String updateResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/update", req, String.class);
            return updateResponse;
        } catch (Exception e) {
            log.error("更新公司协议档案数据异常 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    @RequestMapping("/delete")
    public String delete(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/delete", req, String.class);
            return deleteResponse;
        } catch (Exception e) {
            log.error("删除公司档协议案数据异常 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    @RequestMapping("/searchAccount")
    public String searchAccount(CompanyQuestVO request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CompanyQuestVO> req = new HttpEntity<CompanyQuestVO>(request, headers);
        try {
            String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX + "/collection/companyAgreementFile/searchAccount", req, String.class);
            return deleteResponse;
        } catch (Exception e) {
            log.error("查询账户名数据异常 error={}" + e);
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData.toString();
        }
    }

    /**
     * 调用接口 新增、修改、删除 主协议
     *
     * @param request
     * @return
     */
    @RequestMapping("/generateMainCode")
    @ResponseBody
    public RetData generateMainCode(CompanyAgreementVO request)
    {
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

            log.info("子协议【"+request.getType().toUpperCase()+"】 ai63015 调用响应 result=" + xmlMapper.writeValueAsString(ai63015Response));

            if ("0".equals(ai63015Response.getTRANSOK())) {  //正常

                if("cancel".equals(request.getType())){//取消就地执行数据更新
                    CompanyAgreementVO cVO = new CompanyAgreementVO();
                    cVO.setId(request.getId());
                    cVO.setAgreementStatus("1");

                    HttpHeaders headers = new HttpHeaders();
                    HttpEntity<CompanyAgreementVO> req = new HttpEntity<>(cVO, headers);
                    String deleteResponse = restTemplate.postForObject(REST_URL_PREFIX+"/collection/companyAgreementFile/updateAgreementStatus", req, String.class);
                    Map<String, String> mapRsp = JSONObject.parseObject(deleteResponse, Map.class);
                    if(new Integer(20000).equals(mapRsp.get("code"))){
                        RetData retCancel = RetData.build(StatusEnums.SUCCESS.getStatusCode(), "取消协议成功");
                        return retCancel;
                    }
                    RetData retCancel = RetData.build(StatusEnums.SUCCESS.getStatusCode(), "取消协议成功");
                    return retCancel;
                }

                RetResult retResult = new RetResult().setItem_list(ai63015Response.getPROTSENO());
                return new RetData(StatusEnums.SUCCESS, retResult);
            } else {
                log.error(" ai63015 接口调用出错 RESPONSE_ERROR_CODE=" + ai63015Response.getERR_NO());

                return RetData.build(ai63015Response.getERR_NO(), "接口调用出错：" + ai63015Response.getERR_NO());
            }
        } catch (Exception e) {
            log.error("ai63015 接口调用异常 exception=" + e.getMessage());
            RetData retData = RetData.build(StatusEnums.ERROR.getStatusCode(), "调用ai63015接口异常 exception=" + e.getMessage());
            return retData;
        }
    }

    /**
     * 接口调用参数组装
     *
     * @param caVO
     * @return
     */
    private Ai63015Request buildParam(CompanyAgreementVO caVO)
    {
        Ai63015Request ai63015Request = new Ai63015Request();
        //公共必需参数
        ai63015Request.setPROTFLG(caVO.getSignDriect());//签约方向标志 1发起方；2从属方
        ai63015Request.setPRODSENO(caVO.getProductIndex());//产品序号
        ai63015Request.setFEESENO(caVO.getProductIndex());//费用序号与产品序号一致
        ai63015Request.setMEDIUMID(caVO.getCompanyAccount());//介质识别号
        ai63015Request.setAGTSNAME(caVO.getProductTypeShort());//业务种类简称
        ai63015Request.setPRODTYPE(caVO.getProductType());//产品种类
        ai63015Request.setFEEFLAG(caVO.getChargeSign());//收费标志
        ai63015Request.setCUSTNO(caVO.getCustomerIndex());//客户编码
        ai63015Request.setSIGNCURR("" + Integer.parseInt(caVO.getCcy()));//币种
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

        String ctdate = StringUtils.isEmpty(caVO.getEffectDate()) ? sdf.format(new Date()) : caVO.getEffectDate();
        ai63015Request.setCTDATE(ctdate);//创建日期
        ai63015Request.setACTVDATE(ctdate);//生效日期

        //选填
        if(!StringUtils.isEmpty(caVO.getMaxAmount())){
            ai63015Request.setFIELD1(Math.round(100 * AmountUtil.formatToNumber(new BigDecimal(caVO.getMaxAmount()))) +"");//最高金额
        }
        if(!StringUtils.isEmpty(caVO.getMinAmount())){
            ai63015Request.setFIELD2(Math.round(100 * AmountUtil.formatToNumber(new BigDecimal(caVO.getMinAmount()))) + "");//最低金额
        }
        if(!StringUtils.isEmpty(caVO.getChargeByNumberStandard())) {
            ai63015Request.setFEEAMT(Math.round(100 * AmountUtil.formatToNumber(new BigDecimal(caVO.getChargeByNumberStandard()))) + "");//按笔数收费标准  即费用金额
        }
        if(!StringUtils.isEmpty(caVO.getScalepc())) {
            ai63015Request.setSCALEPC(Math.round(1000000 * AmountUtil.formatToNumber(new BigDecimal(caVO.getScalepc()))) + "");//按金额收费的比例（收费标志为1时不输）
        }

        if ("2".equals(caVO.getSignDriect())) { //新增签约方向为2必输
            ai63015Request.setPROTSENO(caVO.getMainAgreementNumber());//协议编号
        }else if("1".equals(caVO.getSignDriect())){ //如果PROTFLG字段为1，发起方，该字段为空，不填
            ai63015Request.setPROTSENO("");
        }

//        if (!"1".equals(caVO.getChargeSign())) { // 收费标志为1时不输

        ai63015Request.setFEEMETHD(caVO.getChargeMethod());//收费方式（收费标志为1时不输）
//        }

        if (StringUtils.isEmpty(caVO.getMainAgreementNumber())) {
            //动作标识：2. 修改 3. 刪除 4. 新增
            ai63015Request.setOPERFLAG("4");//新增
        } else {
            switch(caVO.getType()){
                case "edit":
                    ai63015Request.setPROTSENO(caVO.getMainAgreementNumber());//修改必传，协议编号
                    ai63015Request.setMAINPTSN(caVO.getMainAgreementNumber());//修改必输，如果不存在则走生成流程
                    ai63015Request.setOPERFLAG("2");//修改
                    break;
                case "cancel":
                    ai63015Request = new Ai63015Request();
                    ai63015Request.setMAINPTSN(caVO.getMainAgreementNumber());//终止必输
                    ai63015Request.setCUSTNO(caVO.getCustomerIndex());//客户编码
                    ai63015Request.setPROTSENO(caVO.getMainAgreementNumber());//修改必传，协议编号
                    ai63015Request.setOPERFLAG("3");//删除
                    break;
            }
            ai63015Request.setLTDATE(sdf.format(new Date()));
        }

        return ai63015Request;
    }

}