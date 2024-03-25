package com.indinner.wallserver.service;


import com.indinner.wallserver.utils.Result;

public interface WxAuthentication {
    public Result getOpenid(String code);
}
