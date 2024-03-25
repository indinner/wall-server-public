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
 * @Date 2023/6/1 14:08
 * @Version 1.0
 * @Doc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "管理员用户")
public class AdminUser {

    @Id
    private String ID;

    @ApiModelProperty(value = "管理员openid")
    private String openid;

    @ApiModelProperty(value = "当前管理员可管理的学校code列表")
    private List<Integer> schoolCodes;

    @ApiModelProperty(value = "绑定唯一QID，绑定用户openid")
    private String QID;


}
