package com.indinner.wallserver.entity;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "推送具体内容")
public class PushMsg {

    private String barkID;

    private String icon="https://cdn.indinner.com/icon/icon-tmq.jpg";

    private String title;

    private String body;

    private String sound="glass";

}
