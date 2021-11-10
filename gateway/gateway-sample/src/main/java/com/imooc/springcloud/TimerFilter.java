package com.imooc.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
//实现GlobalFilter，不用注入Config类，即可实现全局过滤。
//实现GatewayFilter，需要注入Config类并使用，实现gateway过滤。
public class TimerFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        StopWatch timer = new StopWatch();
        timer.start(exchange.getRequest().getURI().getRawPath());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    timer.stop();
                    log.info(timer.prettyPrint());
                })
        );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
