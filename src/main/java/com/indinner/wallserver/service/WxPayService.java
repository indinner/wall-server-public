package com.indinner.wallserver.service;

import com.indinner.wallserver.entity.WxPayCommodity;

import java.util.Map;

public interface WxPayService {
    String JSApiPay(WxPayCommodity commodity);

    String getPaySign(Map<Object,Object > map);
}
