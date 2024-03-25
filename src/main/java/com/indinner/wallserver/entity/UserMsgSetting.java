package com.indinner.wallserver.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户推送信息设置")
public class UserMsgSetting {


    @Id
    private String ID;

    @ApiModelProperty(value = "用户openid")
    private String openid;

    @ApiModelProperty(value = "用户存储的barkID")
    private String barkID;



}
