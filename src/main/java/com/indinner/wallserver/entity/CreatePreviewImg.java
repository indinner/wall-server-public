package com.indinner.wallserver.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @Author indinner
 * @Date 2023/5/31 16:55
 * @Version 1.0
 * @Doc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "预览图")
public class CreatePreviewImg {

    @Id
    private String ID;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "消息")
    private List<String> messages;

    @ApiModelProperty(value = "图片列表")
    private List<String> imgUrlList;

    @ApiModelProperty(value = "标签")
    private String tag;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "微信")
    private String wechat;

    @ApiModelProperty(value = "QQ")
    private String qq;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "最终生成的投稿url地址")
    private String url;

    @ApiModelProperty(value = "学校校区代码，默认为0")
    private Integer schoolCode=0;

    @ApiModelProperty(value = "状态,0待审核,1审核通过已发布,-1审核不通过")
    private Integer status=0;

    @ApiModelProperty(value = "投稿者openid")
    private String openid;

    @ApiModelProperty(value = "墙墙头像")
    private String headImg;

    private String qrCode;

    private String bgColor;

    private String formId;



}
