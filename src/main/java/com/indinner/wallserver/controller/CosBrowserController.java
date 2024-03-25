package com.indinner.wallserver.controller;

import com.indinner.wallserver.utils.CosBrowser.GetCredential;
import com.tencent.cloud.Response;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author indinner
 * @Date 2023/5/30 23:53
 * @Version 1.0
 * @Doc:
 */
@CrossOrigin
@Slf4j
@RequestMapping("/cos")
@RestController
public class CosBrowserController {

    @Resource
    GetCredential getCredential;

    @ApiOperation(value = "获取COS存储桶临时密钥")
    @RequestMapping("/getKey")
    public Response getCredential(){
        return getCredential.getCredential();
    }
}
