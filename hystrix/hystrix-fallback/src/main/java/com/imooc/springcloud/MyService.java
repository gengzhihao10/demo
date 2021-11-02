package com.imooc.springcloud;

import com.imooc.springcloud.hystrix.Fallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "feign-client",fallback = Fallback.class,primary = true)
public interface MyService extends IService {
}
