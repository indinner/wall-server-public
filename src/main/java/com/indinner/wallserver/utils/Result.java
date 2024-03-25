package com.indinner.wallserver.utils;

import lombok.Data;

/**
 * @description: 返回值包装类
 * @author: rbt
 * @email: ddva@163.com
 * @date: 2022/7/20 13:29
 */


@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;


    public Result(){
    }

    public Result(T data){
        this.data=data;
    }

    public static Result success(){
        Result result=new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    public static <T> Result<T> success(T data){
        Result<T> result=new Result<>(data);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    public static Result error(int code,String msg){
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}

