package com.news.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 管理员用户信息表
 * </p>
 *
 * @author mcm
 * @since 2022-03-16
 */
@Data
@TableName("ad_user")
public class AdUser implements Serializable {
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "登录用户名")
    private String name;
    @ApiModelProperty(value = "登录密码")
    private String password;
    @ApiModelProperty(value = "盐")
    private String salt;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "最后一次登录时间")
    private Date loginTime;
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;


}
