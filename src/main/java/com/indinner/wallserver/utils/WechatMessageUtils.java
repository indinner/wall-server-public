package com.indinner.wallserver.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author indinner
 * @Date 2023/6/1 00:59
 * @Version 1.0
 * @Doc:小程序订阅消息推送工具类
 */
@Service
public class WechatMessageUtils {

    public static String toMessage(String openid,String formId, String templateID,JSONObject data){
        String url="https://api.q.qq.com/api/json/template/send?access_token="+WxConfig.Access_token;

        JSONObject body = new JSONObject();
        // 用户的openid
        body.set("touser", openid);
        body.set("form_id",formId);
        // 订阅消息模板ID
        body.set("template_id", templateID);
        // 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
        //body.set("miniprogram_state", "formal为正式版");
        // 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
        body.set("page", "pages/index/index");
        //body.set("lang", "zh_CN");
        body.set("data", data);

        System.out.println(body.toString());

        String post = HttpUtil.post(url,body.toString());
        System.out.println(post);
        return "123";
    }

    public static void barkPush(String barkID,String message,String icon){
        String url="https://api.day.app/"+barkID;
        JSONObject jsonObject=new JSONObject();
        jsonObject.set("body",message);
        jsonObject.set("title","有新的投稿");
        jsonObject.set("icon",icon);
        HttpUtil.post(url,jsonObject.toString());
    }


}
