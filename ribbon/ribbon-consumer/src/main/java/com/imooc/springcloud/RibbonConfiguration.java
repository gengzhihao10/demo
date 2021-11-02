package com.imooc.springcloud;

import com.imooc.springcloud.rules.MyRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

//配置类通用注解
@Configuration
//对某个服务指定负载均衡策略时，可使用这个注解，并指定服务名称和负载均衡策略
@RibbonClient(name = "eureka-client", configuration = MyRule.class)
public class RibbonConfiguration {

    //fixme 两种指定服务负载均衡的配置方式哪种优先级更高
    //@RibbonClient的优先级高于在配置文件中配置

    //fixme property和yml哪种配置文件的优先级更高

//    @Bean
//    public IRule defaultLBStrategy(){
//        return new RandomRule();
//    }
}
