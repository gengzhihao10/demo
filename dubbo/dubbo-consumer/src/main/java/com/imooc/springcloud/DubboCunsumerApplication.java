package com.imooc.springcloud;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableDubbo
public class DubboCunsumerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboCunsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
