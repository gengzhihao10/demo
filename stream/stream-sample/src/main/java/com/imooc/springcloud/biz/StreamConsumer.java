package com.imooc.springcloud.biz;

import com.imooc.springcloud.topics.DelayedTopic;
import com.imooc.springcloud.topics.ErrorTopic;
import com.imooc.springcloud.topics.GroupTopic;
import com.imooc.springcloud.topics.MyTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@EnableBinding(value = {
        Sink.class,
        MyTopic.class,
        GroupTopic.class,
        DelayedTopic.class,
        ErrorTopic.class
}
)
public class StreamConsumer {

    private AtomicInteger count = new AtomicInteger(1);

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

//    @StreamListener(DelayedTopic.INPUT)
//    public void consumeDelayedMessage(MessageBean bean){
//        log.info("Delayed message consumed successfully, payLoad={}", bean.getPayLoad());
//    }

    //异常重试(单机版)
    @StreamListener(ErrorTopic.INPUT)
    public void consumeErrorMessage(Object payLoad){
        log.info("r u ok");
        if (count.incrementAndGet() % 3 == 0){
            log.info("fine, 3ku,and u?");
            count.set(0);
        } else {
            log.info("What's your problem");
            throw new RuntimeException("I'm not OK");
        }
        log.info("Error message consumed successfully, payLoad={}", payLoad);
    }
}
