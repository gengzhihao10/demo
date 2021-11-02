package com.imooc.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    //IService的注解@FeignClient会为该接口实现一个代理类注入进来
    @Autowired
    private IService iService;

    @GetMapping("/sayHi")
    public String sayHi(){
        return iService.sayHi();
    }
}
