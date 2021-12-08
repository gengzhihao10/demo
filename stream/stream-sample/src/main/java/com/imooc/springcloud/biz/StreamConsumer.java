package com.imooc.springcloud.biz;

import com.imooc.springcloud.topics.GroupTopic;
import com.imooc.springcloud.topics.MyTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@Slf4j
@EnableBinding(value = {
        Sink.class,
        MyTopic.class,
        GroupTopic.class
}
)
public class StreamConsumer {

    @StreamListener(Sink.INPUT)
    public void consume(Object payLoad){
      log.info("message consumed successfully, payLoad={}", payLoad);
    }


    @StreamListener(MyTopic.INPUT)
    public void consumeMyMessage(Object payLoad){
        log.info("My message consumed successfully, payLoad={}", payLoad);
    }


    @StreamListener(GroupTopic.INPUT)
    public void consumeGroupMessage(Object payLoad){
        log.info("Group message consumed successfully, payLoad={}", payLoad);
    }
}
