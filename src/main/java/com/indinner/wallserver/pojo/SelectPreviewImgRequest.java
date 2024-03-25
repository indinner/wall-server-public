package com.indinner.wallserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author indinner
 * @Date 2023/6/1 14:48
 * @Version 1.0
 * @Doc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "查询稿件")
public class SelectPreviewImgRequest {

    @ApiModelProperty(value = "管理员openid")
    private String openid;

    @ApiModelProperty(value = "查询学校代码")
    private Integer schoolCode;

    @ApiModelProperty(value = "稿件状态 0待审核,1审核通过已发布,-1审核不通过")
    private Integer status;

    @ApiModelProperty(value = "页码")
    private Integer pageNum;

    @ApiModelProperty(value = "查询的条数")
    private Integer pageSize;
}
