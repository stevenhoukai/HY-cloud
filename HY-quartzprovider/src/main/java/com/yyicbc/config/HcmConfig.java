package com.yyicbc.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "hcm")
public class HcmConfig {

    public String url;

    public String loginUrl;

    public String updateUrl;

    public String updateStatusUrl;

    public String usercode;

    public String pwd;

}
