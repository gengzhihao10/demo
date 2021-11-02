package com.imooc.springcloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface IService {

    //对于不使用spring cloud的三方，应提供给不包括@FeignClient和@GetMapping的另一个包
    @GetMapping("/sayHi")
    public String sayHi();

    @PostMapping("/sayHi")
    public Friend sayHiPost(@RequestBody Friend friend);

    @GetMapping("/retry")
    public String retry(@RequestParam(name  = "timeout") int timeout);

    @GetMapping("/error")
    public String error();
}
