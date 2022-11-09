package com.news.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class ApRegisterDto {

        @ApiModelProperty(value = "用户名")
        private String name;

        /**
         * 密码,md5加密
         */
        @ApiModelProperty(value = "密码,md5加密")
        private String password;

        /**
         * 手机号
         */
        @ApiModelProperty(value = "手机号")
        private String phone;

        /**
         * 性别
         */
        @ApiModelProperty(value = "性别")
        private String sex;

        @ApiModelProperty(value = "是否有效")
        private Boolean status;
    }


