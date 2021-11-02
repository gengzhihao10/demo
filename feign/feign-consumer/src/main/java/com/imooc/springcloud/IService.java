package com.imooc.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client")
public interface IService {

    //假入调用IService的sayHi方法，会调用eureka-client的/sayHi路径
    @GetMapping("sayHi")
    String sayHi();

}
