package com.imooc.springcloud.topics;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyTopic {

    String INPUT= "myTopic-consumer";

    String OUTPUT ="myTopic-producer";

    //input是消费者那一方（接收消息的）
    //output是生产者那一方（推送消息的）
    //SubscribaleChannel意味可被订阅的channel
    @Input(INPUT)
    SubscribableChannel input();

    //两个topic被注入到channel的时候，还会被作为一个bean注入进来，如果input和output名字重复了，就会报bean重复异常。
    //因此input和output需要不同的名字，为此还需要在配置文件中将两个名字绑定
    @Output(OUTPUT)
    MessageChannel output();
}
