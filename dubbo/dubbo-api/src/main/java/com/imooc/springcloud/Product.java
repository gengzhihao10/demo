package com.imooc.springcloud;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Product implements Serializable {

    private String name;

    private BigDecimal price;
}
