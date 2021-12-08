package com.imooc.springcloud.biz;

import com.imooc.springcloud.topics.GroupTopic;
import com.imooc.springcloud.topics.MyTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Controller {

    @Autowired
    private MyTopic producer;

    @Autowired
    private GroupTopic groupTopicProducer;

    @PostMapping("send")
    public void sendMessage(@RequestParam String body){
        producer.output().send(MessageBuilder.withPayload(body).build());
    }

    @PostMapping("sendToGroup")
    public void sendMessageToGroup(@RequestParam String body){
        groupTopicProducer.output().send(MessageBuilder.withPayload(body).build());
    }
}
