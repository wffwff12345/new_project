package com.news.appuser.dto;

import lombok.Data;

@Data
public class LoginDto {

    //设备id
    private String equipmentId;

    //手机号
    private String name;

    //密码
    private String password;
}