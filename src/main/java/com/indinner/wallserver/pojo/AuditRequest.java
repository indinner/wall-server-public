package com.indinner.wallserver.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author indinner
 * @Date 2023/6/1 16:28
 * @Version 1.0
 * @Doc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "审核")
public class AuditRequest {

    private String previewID;

    private String openid;

    private Integer status;

    private String formId;

}
