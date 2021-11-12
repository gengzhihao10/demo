package com.imooc.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import java.time.ZonedDateTime;

@Configuration
public class GatewayConfiguration {

    @Autowired
    private TimerFilter timerFilter;

    @Autowired
    private AuthFilter authFilter;

    @Bean
    @Order
    public RouteLocator customizedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r ->
                        //该path断言要求路径匹配到/java/**
                        r.path("/java/**")
                                //该method断言要求方法为GET
                                .and().method(HttpMethod.GET)
                                //该header断言要求header中包括"name"
                                .and().header("name")
                                //类似于配置文件中的StripPrefix
                                .filters(f -> f.stripPrefix(1)
                                        //给响应中添加header
                                        .addResponseHeader("java-param", "gateway-config")
                                        //指定自定义的filter
                                        .filter(timerFilter)
                                        .filter(authFilter)
                                )
                                .uri("lb://FEIGN-CLIENT")
                )
                .route(r -> r.path("/seckill/**")
                                //after断言可以让gateway的路由规则只在某个时间点以后生效(推迟一分钟)
                                .and().after(ZonedDateTime.now().plusMinutes(1))
//                        .and().before()
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://FEIGN-CLIENT")
                )
                .build();
    }


}
