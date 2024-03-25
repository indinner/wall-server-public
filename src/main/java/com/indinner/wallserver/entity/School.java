package com.indinner.wallserver.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @Author indinner
 * @Date 2023/6/1 15:14
 * @Version 1.0
 * @Doc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "学校管理")
public class School {

    @Id
    private String ID;

    private Integer schoolCode;

    private String schoolName;

    private String adminID;

    @ApiModelProperty(value = "稿件审核通过后的文案")
    private String successMsg;

    @ApiModelProperty(value = "墙墙头像")
    private String headImg;

    private String barkID;

    private String qrCode;

}
