package com.imooc.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/sayHi")
    public String sayHi() {
        return restTemplate.getForObject(
                //与之前eureka章节不同的是，这里不用通过LoadBalanceClient提供敷在均衡服务，
                //并且不用在controller中通过代码指定ip和端口
                //而是在注入restTemplate的时候，给他配置一个loadBalanceClient，具体可在启动类处查看
                "http://eureka-client/sayHi",
                String.class);
    }
}
