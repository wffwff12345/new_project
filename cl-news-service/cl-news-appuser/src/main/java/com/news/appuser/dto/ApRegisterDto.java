package com.news.appuser.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
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


    }


