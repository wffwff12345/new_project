package com.news.wemedia.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WmLoginDto {
    @ApiModelProperty(value = "用户登录名")
    private  String name;
    @ApiModelProperty(value = "登录密码")
    private  String password;
}
