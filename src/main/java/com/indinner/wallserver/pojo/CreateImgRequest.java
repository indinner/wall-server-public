package com.indinner.wallserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author indinner
 * @Date 2023/5/31 16:54
 * @Version 1.0
 * @Doc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "预览图")
public class CreateImgRequest {

    @NotBlank
    @ApiModelProperty(value = "标题")
    private String title;

    @NotNull
    @ApiModelProperty(value = "消息")
    private List<String> messages;

    @ApiModelProperty(value = "图片列表")
    private List<String> imgUrlList;

    @NotBlank
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

    @NotBlank
    @ApiModelProperty(value = "最终生成的投稿url地址")
    private String url;

    @ApiModelProperty(value = "学校校区代码，默认为0")
    private Integer schoolCode=0;

    @ApiModelProperty(value = "投稿者openid")
    private String openid;

    private String bgColor;

    private String formId;
}
