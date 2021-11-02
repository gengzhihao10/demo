package com.imooc.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeignConsumerApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FeignConsumerApp.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    //FIXME 这里有一个关于扫包的坑，一定要注意一下
    /** 这里Controller中可以直接将IServiceAutowired进来，是巧合（@EnableFeignClients会将com.imooc.springcloud包下的扫进来，而IService的包同名）
     * 而一般包名不同时，想要将接口或者类扫进来，有两种方式
     * 1. 在@EnableFeignClients中添加包路径
     * 2. 添加一个@FeignClient注解
     * 一般来说第二种方式更好一些，更加灵活
     */

    /**
     * 2. 添加一个@FeignClient注解
     *
     * 在F版及以前：
     * 由于IService也有一个@FeignClients注解，
     * 如果@FeignClient在子接口和父接口都有，重复了，
     * 需要指定哪个FeignClient是优先的（primary = true）
     * 这样指定优先FeignClient的做法，
     * 在F版及以前是可以的，但是在G版中只有FeignClient注解是不行的，会报错
     *
     * G版：
     * 不过可以通过配置文件中进行配置，允许bean的重载。再加上FeignClient注解，就OK了，注解就可以工作了
     * eg. spring.main.allow-bean-definition-overriding = true
     */
    @FeignClient(value = "f", primary = true)
    public interface MyService extends IService{

    }


}
