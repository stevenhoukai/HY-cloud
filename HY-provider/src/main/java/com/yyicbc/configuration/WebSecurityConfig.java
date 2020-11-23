package com.yyicbc.configuration;

import com.yyicbc.beans.utils.distribute.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全配置类
 * 该类配置后系统全部接口受security保护
 * 需要对actuator进行保护
 * @author stv
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.user.roles}")
    private String roles;

    @Value("${spring.security.user.name}")
    private String name;

    @Value("${spring.security.user.password}")
    private String password;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable();
//        http
//                .authorizeRequests()
//                .requestMatchers(EndpointRequest.toAnyEndpoint())
//                .hasRole("ACTUATOR_ADMIN")
//                .antMatchers("/").permitAll()
//                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable();
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/actuator").authenticated()
                .antMatchers("/actuator/*").authenticated()
                .and().csrf().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(name)
                .password(new BCryptPasswordEncoder().encode(password))
                .roles(roles);
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //分布式主键生成器
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

}
