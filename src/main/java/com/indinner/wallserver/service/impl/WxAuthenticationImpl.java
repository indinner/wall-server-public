package com.indinner.wallserver.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

import com.indinner.wallserver.service.WxAuthentication;
import com.indinner.wallserver.utils.Result;
import com.indinner.wallserver.utils.ResultCode;
import com.indinner.wallserver.utils.WxConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
@Slf4j
@EnableScheduling
public class WxAuthenticationImpl implements WxAuthentication {

    @Resource
    WxConfig wxConfig;

    /**
     * 获取openid*/
    @Override
    public Result getOpenid(String code) {
        log.info("入参code:{}",code);
        String Url="https://api.q.qq.com/sns/jscode2session?appid="+wxConfig.getAppid()+"&secret="+wxConfig.getSecret()+"&js_code="+code+"&grant_type=authorization_code";
        HttpRequest request= HttpRequest.get(Url);
        HttpResponse response=request.execute();
        JSONObject jsonObject=new JSONObject(response.body());
        if(jsonObject.getStr("openid")!=null){
            return Result.success(jsonObject.getStr("openid"));
        }else {
            return Result.error(ResultCode.FAILED.getCode(), "获取openid失败.原因:"+response.body());
        }
    }

    /**
     * 获取用户手机号*/
    public String getPhoneNumber(String code){
        String Url="https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token="+WxConfig.Access_token;
        JSONObject jsonObject=new JSONObject();
        jsonObject.set("code",code);
        String result= HttpUtil.post(Url,jsonObject.toString());
        return result;
    }


    /**
     * 获取Access_token并存储到WxConfig
     * 每小时刷新一次*/
    @PostConstruct
    @Scheduled(cron = "0 0 * * * *")
    public void getAccessToken(){
        String Url="https://api.q.qq.com/api/getToken?grant_type=client_credential&appid="+wxConfig.getAppid()+"&secret="+wxConfig.getSecret();
        HttpRequest request=HttpRequest.get(Url);
        HttpResponse response=request.execute();
        log.info("获取Access_token结果如下:"+response.body());
        JSONObject jsonObject=new JSONObject(response.body());
        WxConfig.Access_token=jsonObject.getStr("access_token");
    }
}
