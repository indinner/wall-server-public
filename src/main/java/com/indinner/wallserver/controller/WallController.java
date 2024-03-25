package com.indinner.wallserver.controller;

import com.indinner.wallserver.entity.CreatePreviewImg;
import com.indinner.wallserver.entity.School;
import com.indinner.wallserver.pojo.AuditRequest;
import com.indinner.wallserver.pojo.CreateImgRequest;
import com.indinner.wallserver.pojo.SelectPreviewImgRequest;
import com.indinner.wallserver.service.WallService;
import com.indinner.wallserver.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author indinner
 * @Date 2023/5/31 20:14
 * @Version 1.0
 * @Doc:
 */
@RestController
@Slf4j
@RequestMapping("/wall")
@Api(value = "投稿相关接口")
public class WallController {

    @Resource
    WallService wallService;

    @ApiOperation(value = "生成预览图返回")
    @PostMapping("/createPreviewImg")
    public Result<String> createPreviewImg(@RequestBody CreateImgRequest createImgRequest) throws Exception {
        return wallService.createImg(createImgRequest);
    }

    @ApiOperation(value = "发布稿件")
    @PostMapping("/toMessage")
    public Result<String> toMessage(@RequestBody @Valid CreateImgRequest createImgRequest){
        return wallService.toMessage(createImgRequest);
    }

    @ApiOperation(value = "查询学校")
    @GetMapping("/selectSchool")
    public Result<List<School>> selectSchool(@RequestParam String adminID){
        return wallService.selectSchool(adminID);
    }

    @ApiOperation(value = "查询稿件")
    @PostMapping("/selectPreviewImg")
    public Result<List<CreatePreviewImg>> selectPreviewImg(@RequestBody SelectPreviewImgRequest selectPreviewImgRequest){
        return wallService.selectPreviewImg(selectPreviewImgRequest);
    }

    @ApiOperation(value = "审核稿件")
    @PostMapping("/audit")
    public Result<String> audit(@RequestBody AuditRequest auditRequest){
        return wallService.audit(auditRequest);
    }

    @ApiOperation(value = "判断是否是管理员")
    @GetMapping("/isAdmin")
    public Result<Boolean> isAdmin(@RequestParam String openid){
        return wallService.isAdmin(openid);
    }

    @ApiOperation(value = "绑定用户")
    @GetMapping("/addUser")
    public Result<String> addUser(@RequestParam String openid, @RequestParam String QID){
        return wallService.addUser(openid, QID);
    }

    @ApiOperation(value = "查询用户QID")
    @GetMapping("/selectQID")
    public Result<String> selectQID(@RequestParam String openid){
        return wallService.selectQID(openid);
    }

    @ApiOperation(value = "查询用户QID")
    @GetMapping("/selectAdminQID")
    public Result<String> selectAdminQID(@RequestParam String openid){
        return wallService.selectAdminQID(openid);
    }

}
