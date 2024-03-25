package com.indinner.wallserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JSAPI支付商品信息类*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxPayCommodity {
    /*商品描述*/
    String description;
    /*商户订单号*/
    String out_trade_no;
    /*订单金额*/
    int total;
    /*顾客OPENID*/
    String openid;
}