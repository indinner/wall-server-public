package com.indinner.wallserver.controller;



import com.indinner.wallserver.service.impl.WxAuthenticationImpl;
import com.indinner.wallserver.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/wechat/wx")
@Api(value = "小程序认证接口")
public class WxAuthenticationController {


    @Resource
    WxAuthenticationImpl wxAuthentication;

    @ApiOperation(value = "获取小程序用户Openid")
    @RequestMapping("/getopenid")
    public Result getOpenid(@RequestParam String code){
        return wxAuthentication.getOpenid(code);
    }

}
