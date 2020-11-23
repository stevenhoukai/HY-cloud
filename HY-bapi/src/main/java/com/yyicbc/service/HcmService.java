package com.yyicbc.service;

public interface HcmService
{
    /**
     * 发送主子数据
     */
    void sendCompensationData();

    /**
     * 同步代理系统主表状态(batch_status)到 HCM
     */
    void sendMainStatus();
}
