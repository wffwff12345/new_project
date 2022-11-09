package com.news.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WmRegisterOrEditorDto {

    private static final long serialVersionUID = 1L;

    /**
     * 登录用户名
     */
    private String name;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */

    private String email;

}