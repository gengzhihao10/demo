package com.imooc.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Controller {

    @Autowired
    private MyService myService;

    @GetMapping("/sayHi")
    public String sayHi(){
        return myService.sayHi();
    }

    @PostMapping("/sayHi")
    public Friend sayHi2(){
        Friend friend = new Friend();
        friend.setName("test");
        return myService.sayHiPost(friend);
    }

    @GetMapping("/retry")
    public String retry(Integer timeout){
        return myService.retry(timeout);
    }
}
