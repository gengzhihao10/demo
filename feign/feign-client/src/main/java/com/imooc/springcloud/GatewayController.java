package com.imooc.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
@RequestMapping("gateway")
public class GatewayController {

    public static final Map<Long, Product> items = new ConcurrentHashMap<Long, Product>();

    @RequestMapping(value = "details",method = RequestMethod.GET)
    @ResponseBody
    public Product get(@RequestParam("pid") Long pid){
        //因为只是一个demo，没有访问数据库，所以用一个map临时装数据。访问的时候临时加一个数据进去。
        if (!items.containsKey(pid)){
            Product prod = Product.builder()
                    .productId(pid)
                    .description("好吃不贵")
                    .stock(100L)
                    .build();
            //如果是没有再put进去
            items.putIfAbsent(pid, prod);
        }
        return items.get(pid);
    }

    @RequestMapping(value = "placeOrder",method = RequestMethod.POST)
//    @ResponseBody
    public String buy(@RequestParam("pid") Long pid){
        Product prod = items.get(pid);

        if (prod == null){
            return "Product no null";
        }else if (prod.getStock() <= 0L){
            return "Sold out";
        }

        //当有两个请求同时访问这个方法，就要锁住产品，判断下是否超卖
        synchronized (prod){
            if (prod.getStock() <= 0L){
                return "Sold out";
            }
            prod.setStock(prod.getStock() - 1);
        }
        return "Order Placed";
    }
}
