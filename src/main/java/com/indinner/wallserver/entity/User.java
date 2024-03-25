package com.indinner.wallserver.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @Author indinner
 * @Date 2023/6/1 21:45 
 * @Version 1.0 
 * @Doc:
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户")
public class User {

    @Id
    private String ID;

    private String openid;

    private String QID;
}
