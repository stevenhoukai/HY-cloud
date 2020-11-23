package com.yyicbc.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * 安全配置类
 * 该类配置后系统全部接口受security保护
 * 需要对actuator进行保护
 * @author stv
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/actuator").authenticated()
                .antMatchers("/actuator/*").authenticated()
                .and().csrf().disable();
    }


}
