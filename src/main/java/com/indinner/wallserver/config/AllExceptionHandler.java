package com.indinner.wallserver.config;

import com.indinner.wallserver.utils.Result;
import com.indinner.wallserver.utils.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 全局异常处理器
 * @author: rbt
 * @email: ddva@163.com
 * @date: 2022/7/20 13:54
 */
@RestControllerAdvice
public class AllExceptionHandler {
    //进行异常处理，处理Exception.class异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e){
        e.printStackTrace();
        return Result.error(ResultCode.UNKNOWN_ABNORMAL.getCode(), ResultCode.UNKNOWN_ABNORMAL.getMsg());
    }
}
