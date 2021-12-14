package com.imooc.springcloud.biz;

import com.imooc.springcloud.topics.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@EnableBinding(value = {
        Sink.class,
        MyTopic.class,
        GroupTopic.class,
        DelayedTopic.class,
        ErrorTopic.class,
        RequeueTopic.class,
        DLQTopic.class,
        FallBackTopic.class
}
)
public class StreamConsumer {

    private AtomicInteger count = new AtomicInteger(1);

    @StreamListener(Sink.INPUT)
    public void consume(Object payLoad) {
        log.info("message consumed successfully, payLoad={}", payLoad);
    }


    @StreamListener(MyTopic.INPUT)
    public void consumeMyMessage(Object payLoad) {
        log.info("My message consumed successfully, payLoad={}", payLoad);
    }


    @StreamListener(GroupTopic.INPUT)
    public void consumeGroupMessage(Object payLoad) {
        log.info("Group message consumed successfully, payLoad={}", payLoad);
    }

//    @StreamListener(DelayedTopic.INPUT)
//    public void consumeDelayedMessage(MessageBean bean){
//        log.info("Delayed message consumed successfully, payLoad={}", bean.getPayLoad());
//    }

    //异常重试(单机版)
    @StreamListener(ErrorTopic.INPUT)
    public void consumeErrorMessage(Object payLoad) {
        log.info("r u ok");
        if (count.incrementAndGet() % 3 == 0) {
            log.info("fine, 3ku,and u?");
            count.set(0);
        } else {
            log.info("What's your problem");
            throw new RuntimeException("I'm not OK");
        }
        log.info("Error message consumed successfully, payLoad={}", payLoad);
    }

    //异常重试(联机版)
    @StreamListener(RequeueTopic.INPUT)
    public void requeueErrorMessage(Object payLoad) {
        log.info("r u ok");
        try {
            Thread.sleep(3000L);
        } catch (Exception e) {
        }
        throw new RuntimeException("I'm not OK");

    }

    //死信队列
    @StreamListener(DLQTopic.INPUT)
    public void consumeDLQMessage(Object payLoad) {
        log.info("  DLQ - r u ok");
        if (count.incrementAndGet() % 3 == 0) {
            log.info("DLQ - fine, 3ku,and u?");
        } else {
            log.info("DLQ - What's your problem");
            throw new RuntimeException("DLQ - I'm not OK");
        }
        log.info("DLQ - Error message consumed successfully, payLoad={}", payLoad);
    }


    //Fallback+升版
    @StreamListener(FallBackTopic.INPUT)
    public void goodbyeBadGut(Object payLoad,
                              @Header("version") String version) {
        log.info("  Fallback - r u ok");
        if ("1.0".equalsIgnoreCase(version)) {
            log.info("Fallback - fine, 3ku,and u?");
        }
        else if ("2.0".equalsIgnoreCase(version)){
            log.info("Fallback - Unsurported Version");
            throw new RuntimeException("DLQ - I'm not OK");
        }
        else {
            log.info("Fallback - version={}",version);
        }
        log.info("Fallback - Error message consumed successfully, payLoad={}", payLoad);
    }

    @ServiceActivator(inputChannel = "fallback-topic.fallback-group.errors")
    public void fallback(Message<?>message){
        log.info("fallback entered");
    }
}
