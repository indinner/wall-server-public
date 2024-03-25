package com.indinner.wallserver.utils;

/**
 * @description: 微信基本配置类
 * @author: rbt
 * @email: ddva@163.com
 * @date: 2022/7/20 13:33
 */

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.ScheduledUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Data;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "wx")
public class WxConfig {
    /*小程序Allkilled 配置信息*/
    private String appid;
    private String secret;
    public static String Access_token;       //接口调用凭证


    //商户号
    String mch_id;
    //商户API证书序列号
    String mac_serial_no;
    //商户私钥文件
    String private_key_path;
    //APIV3密钥
    String api_v3_key;
    //微信服务器地址
    String domain;
    //接收结果通知地址
    String no_domain;
    //微信支付统一下单地址
    String wxpay_url;
    //微信公众号名下Openid前六位，以区别openid是否所属该公众号
    String intercept_openid;



    /**
     * 获取签名验证器
     * @return */
    @Bean
    public ScheduledUpdateCertificatesVerifier getVerifier(){
        /*获取商户私钥*/
        PrivateKey privateKey=getPrivateKey(private_key_path);
        /*私钥签名对象*/
        PrivateKeySigner privateKeySigner=new PrivateKeySigner(mac_serial_no,privateKey);
        /*身份认证对象*/
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(mch_id, privateKeySigner);
        // 使用定时更新的签名验证器，不需要传入证书
        ScheduledUpdateCertificatesVerifier verifier = new ScheduledUpdateCertificatesVerifier(
                wechatPay2Credentials,
                api_v3_key.getBytes(StandardCharsets.UTF_8));
        return verifier;
    }

    /**
     * 获取http请求对象
     * @param verifier
     * @return */
    @Bean
    public CloseableHttpClient getWxPayClient(ScheduledUpdateCertificatesVerifier verifier){
        /*获取商户私钥*/
        PrivateKey privateKey=getPrivateKey(private_key_path);
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mch_id, mac_serial_no, privateKey)
                .withValidator(new WechatPay2Validator(verifier));
        // ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        CloseableHttpClient httpClient = builder.build();
        return httpClient;
    }

    /**
     * 获取私钥文件
     * @param filename
     * @return */
    public PrivateKey getPrivateKey(String filename){
        String privateKey="";
        return PemUtil.loadPrivateKey(
                new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 获取公钥文件
     * @param filename
     * @return */
    public PublicKey getPublicKey(String filename){
        String publicKey="";
        return cn.hutool.crypto.PemUtil.readPemPublicKey(
                new ByteArrayInputStream(publicKey.getBytes(StandardCharsets.UTF_8)));
    }

}

