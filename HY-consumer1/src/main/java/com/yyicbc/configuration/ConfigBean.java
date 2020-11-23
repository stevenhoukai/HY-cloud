package com.yyicbc.configuration;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

/**
 * @author stv
 * 客户端负载均衡类
 */

@Configuration
public class ConfigBean {
    /**
     * 配置RestTemplate
     * 通过RestTemplate调用提供者服务 ，发送rest请求
     * 提供了多种访问http服务的方法，
     * 针对于访问rest服务<strong>客户端</strong>的调用的模板类
     */
    @Bean
    @LoadBalanced //ribbon实现的一套 ==客户端、负载均衡的工具类似于nginx
    public  RestTemplate getRestTemplate(){
        RestTemplate template = new RestTemplate();
        template.setInterceptors(Collections.singletonList(headerInterceptor(null,null)));
        return template;
    }


    /***
     * 客户端可以自定义ribbon的负载均衡算法
     * com.netflix.loadbalancer.RoundRobinRule
     *
     * 轮询：默认
     *
     * com.netflix.loadbalancer.RandomRule
     *
     * 随机
     *
     * com.netflix.loadbalancer.AvailabilityFilteringRule
     *
     * 会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务、还有并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问
     *
     * com.netflix.loadbalancer.WeightedResponseTimeRule
     *
     * 根据平均响应时间计算所有服务的权重，响应时间越快的服务权重越大，选中的概率越高。刚启动时如果统计信息不足，则上有RoundRobinRule策略，等统计信息足够，会切换到WeightedResponseTimeRule
     *
     * com.netflix.loadbalancer.RetryRule
     *
     * 先按RoundRobinRule轮询算法获取服务，如果失败则在指定时间内进行重试
     *
     * com.netflix.loadbalancer.BestAvailableRule
     *
     * 会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
     *
     * com.netflix.loadbalancer.ZoneAvoidanceRule
     *
     * 默认规则，复合判断Server所在区域的性能和Server的可用性选择服务
     *
     */

    //自定义负载均衡规则,会在eureka注册多个服务的时候出现大坑解决方案见：
    //
//    @Bean
//    public IRule getIRule(){
//        return new RandomRule();
//    }


    /**
     * 这个bean用于从当前请求中获取token信息，并将信息写入转发的请求头
     *
     * @param value
     * @return
     */
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public ClientHttpRequestInterceptor headerInterceptor(
            @Value("#{request.getHeader('Authorization')}") final String value,
            @Value("#{request.getHeader('UserInfo')}") final String userInfo) {
        return new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException {
                System.out.print(request.getURI()+","+request.getMethod());
                request.getHeaders().add("Authorization", value);
                request.getHeaders().add("UserInfo", userInfo);
                request.getHeaders().add("Accept", "application/json, text/plain, */*");
                return execution.execute(request, body);
            }
        };

    }
}
