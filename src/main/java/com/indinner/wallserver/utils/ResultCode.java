package com.indinner.wallserver.utils;

import lombok.Getter;

/**
 * @description: http响应状态吗
 * @author: rbt
 * @email: ddva@163.com
 * @date: 2022/7/20 13:45
 */
@Getter
public enum ResultCode {
    SUCCESS(200,"请求成功"),
    FAILED(100,"请求失败"),
    UNKNOWN_ABNORMAL(444,"未知异常"),
    INSERT_FAILED(300,"插入数据失败");


    private int code;
    private String msg;

    ResultCode(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
