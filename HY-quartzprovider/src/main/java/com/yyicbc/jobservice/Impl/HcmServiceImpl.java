package com.yyicbc.jobservice.Impl;


import com.alibaba.fastjson.JSONObject;
import com.yyicbc.beans.business.PO.CompensationPO;
import com.yyicbc.beans.business.VO.CompensationVO;
import com.yyicbc.beans.business.VO.SendHCMUpdateVO;
import com.yyicbc.beans.querycondition.CompensationQuestVO;
import com.yyicbc.beans.utils.HttpUtil;
import com.yyicbc.beans.utils.distribute.method.BeanUtil;
import com.yyicbc.config.HcmConfig;
import com.yyicbc.dao.CompensationDao;
import com.yyicbc.jobservice.CompensationService;
import com.yyicbc.service.HcmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class HcmServiceImpl implements HcmService
{
    @Resource
    CompensationService compensationService;

    @Resource
    CompensationDao compensationDao;

    @Resource
    HcmConfig hcmConfig;

    private String loginHcm()
    {
        Map<String, Object> loginParamMap = new HashMap<>();
        loginParamMap.put("usercode", this.hcmConfig.getUsercode());
        loginParamMap.put("pwd", this.hcmConfig.getPwd());

        Map<String, String> headerParamMap = new HashMap<>();
        headerParamMap.put("uap_dataSource", "design");

        String loginUrl = String.format("%s%s", this.hcmConfig.getUrl(), this.hcmConfig.getLoginUrl());
        log.info("HCM 请求登录接口 loginUrl={}",loginUrl);
        log.info("HCM 请求登录接口入参 loginParamMap={}",loginParamMap.toString());

        JSONObject loginJsonRet = HttpUtil.post(loginUrl, loginParamMap, headerParamMap, JSONObject.class);
        log.info("HCM 请求登录接口出参 loginJsonRet={}", loginJsonRet.toString());

        return loginJsonRet == null ? null : loginJsonRet.toString();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void sendCompensationData()
    {
        CompensationQuestVO compensationQuestVO = new CompensationQuestVO();
        compensationQuestVO.setBatchStatus(1);
        List<SendHCMUpdateVO> sendHCMUpdateVOS = compensationService.getAll(compensationQuestVO);
        if (CollectionUtils.isEmpty(sendHCMUpdateVOS)) {
            log.info("查询出 sendHCMUpdateVOS 为空，任务正常退出");
            return;
        }

        String loginStringRet = loginHcm();
        if (null == loginStringRet) {
            log.info("HCM 请求登录失败");
            return;
        }

        Map<String, String> headersMap = JSONObject.parseObject(loginStringRet, Map.class);
        if (headersMap.isEmpty() || StringUtils.isEmpty(headersMap.get("uap_token"))) {
            return;
        }

        Map<String, Object> sendParamMap = new HashMap<>();
        sendParamMap.put("sendData", sendHCMUpdateVOS);
        String updateUrl = String.format("%s%s", this.hcmConfig.getUrl(), this.hcmConfig.getUpdateUrl());
        log.info("HCM 请求更新接口 updateUrl={}", updateUrl);
        log.info("HCM 请求更新接口入参 headers={} sendParamMap={}", headersMap.toString(), sendParamMap.toString());
        JSONObject updateJsonRet = HttpUtil.post(updateUrl, sendParamMap, headersMap, JSONObject.class, "GBK");
        if (null == updateJsonRet) {
            log.info("HCM 数据更新失败");
            return;
        }
        log.info("HCM 请求更新接口出参 updateJsonRet={}", updateJsonRet.toString());

        if (updateJsonRet.isEmpty() || !"20000".equals(updateJsonRet.get("code"))) {
            log.info("请求返回 json 为空或者code！=20000");
            return;
        }

        List<Long> compensationIds = new ArrayList<>();
        for (SendHCMUpdateVO sendHCMUpdateVO : sendHCMUpdateVOS) {
            if (sendHCMUpdateVO.getCompensationId() == null) {
                continue;
            }
            compensationIds.add(Long.valueOf(sendHCMUpdateVO.getCompensationId()));
        }
        compensationService.updateSendStatus(compensationIds);
    }

    public void sendMainStatus()
    {
        List<CompensationPO> compensationPOS = compensationDao.findAll();
        if (CollectionUtils.isEmpty(compensationPOS)) {
            log.info("查询出 sendHCMUpdateVOS 为空，任务正常退出");
            return;
        }

        String loginStringRet = loginHcm();
        if (null == loginStringRet) {
            log.info("HCM 请求登录失败");
            return;
        }

        Map<String, String> headersMap = JSONObject.parseObject(loginStringRet, Map.class);
        if (headersMap.isEmpty() || StringUtils.isEmpty(headersMap.get("uap_token"))) {
            return;
        }

        ArrayList<CompensationVO> compensationVOs = new ArrayList<>();
        CompensationVO compensationVO = new CompensationVO();
        for(CompensationPO compensationPO : compensationPOS){
            compensationVO.setPk_payroll(compensationPO.getPk_payroll());
            compensationVO.setBatchStatus(compensationPO.getBatchStatus());
            compensationVOs.add(compensationVO);
        }
        String updateStatusUrl = String.format("%s%s", this.hcmConfig.getUrl(), this.hcmConfig.getUpdateStatusUrl());
        log.info("HCM 请求更新接口 updateStatusUrl={}", updateStatusUrl);
        log.info("HCM 请求更新接口入参 headers={} sendParamMap={}", headersMap.toString(), compensationVO.toString());
        JSONObject updateJsonRet = HttpUtil.post(updateStatusUrl, compensationVOs, headersMap, JSONObject.class, "GBK");
        if (null == updateJsonRet) {
            log.info("HCM 数据状态更新失败");
            return;
        }
    }

}
