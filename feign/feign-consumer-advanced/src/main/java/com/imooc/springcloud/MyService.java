package com.imooc.springcloud;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "feign-client")
public interface MyService extends IService{
}
