package com.indinner.wallserver.controller;

import com.indinner.wallserver.entity.WxPayCommodity;
import com.indinner.wallserver.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@CrossOrigin
@Slf4j
@RequestMapping("/wechat/pay")
@RestController
public class WxPayController {
    @Resource
    WxPayService wxPayService;


    /**
     * 微信JSAPI支付接口
     * @param map 商品信息
     * @return 预支付交易会话号*/
    @RequestMapping("jsapi")
    public String JSAPI(@RequestBody Map<Object,Object> map){
        WxPayCommodity commodity=new WxPayCommodity();
        commodity.setOpenid((String) map.get("openid"));
        commodity.setTotal((Integer) map.get("total"));
        commodity.setOut_trade_no((String) map.get("out_trade_no"));
        commodity.setDescription((String) map.get("description"));
        String data=wxPayService.JSApiPay(commodity);
        return data;
    }


    /**
     * 返回加密后的签名串*/
    @RequestMapping("getPaySign")
    public String getPaySign(@RequestBody Map<Object,Object > map){
        return wxPayService.getPaySign(map);
    }

}
