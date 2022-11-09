package com.news.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdUserDto {
    @ApiModelProperty(value = "用户登录名")
    private String name;
    @ApiModelProperty(value = "登录密码")
    private String password;
}
