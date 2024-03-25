package com.indinner.wallserver.service.impl;

import cn.hutool.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.indinner.wallserver.entity.WxPayCommodity;
import com.indinner.wallserver.service.WxPayService;
import com.indinner.wallserver.utils.WxConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Map;


@Service
@Slf4j
@EnableScheduling
public class WxPayServiceImpl implements WxPayService {

    @Resource
    WxConfig wxConfig;

    @Resource
    CloseableHttpClient getWxPayClient;

    JSONObject getJson(String key,Object value){
        JSONObject jsonObject=new JSONObject();
        jsonObject.set(key,value);
        return jsonObject;
    }


    /**
     * 微信支付 JSAPI支付
     * @param commodity 商品信息类
     * @return 返回微信支付预支付ID  prepay_id*/
    @Override
    public String JSApiPay(WxPayCommodity commodity) {
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid",wxConfig.getMch_id())
                .put("appid", wxConfig.getAppid())
                .put("description", commodity.getDescription())
                .put("notify_url", "https://www.rbtcmm.cn")
                .put("out_trade_no", commodity.getOut_trade_no());
        rootNode.putObject("amount")
                .put("total", commodity.getTotal());
        rootNode.putObject("payer")
                .put("openid", commodity.getOpenid());

        try {
            objectMapper.writeValue(bos, rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CloseableHttpResponse response = null;
        try {
            response = getWxPayClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String bodyAsString = null;
        try {
            bodyAsString = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("------------");
        System.out.println(bodyAsString);
        return bodyAsString;
    }

    /**
     * JSAPI支付
     * 参数如下：
     * appid
     * timeStamp
     * nonceStr
     * prepay_id
     * 返回加密后的签名串*/
    public String getPaySign(Map<Object,Object > map){
        String appid= (String) map.get("appid");
        String timeStamp= (String) map.get("timeStamp");
        String nonceStr= (String) map.get("nonceStr");
        String prepay_id= (String) map.get("prepay_id");
        StringBuilder builder=new StringBuilder();
        builder.append(appid).append("\n");
        builder.append(timeStamp).append("\n");
        builder.append(nonceStr).append("\n");
        builder.append(prepay_id).append("\n");
        System.out.println(builder);
        return sign(builder.toString().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * sha256 with rsa签名算法*/
    String sign(byte[] message) {
        Signature sign = null;
        try {
            sign = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sign.initSign(wxConfig.getPrivateKey("null"));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            sign.update(message);
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        try {
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return "null";
    }


    

}
