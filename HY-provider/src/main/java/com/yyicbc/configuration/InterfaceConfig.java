package com.yyicbc.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "icbc-interface")
public class InterfaceConfig
{
    /**
     * 户名接口配置
     */
    public String cifcnamenameAccno;

    /**
     * 主协议接口配置
     */
    public String flowc;
}
