package com.news.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 自媒体用户信息表
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wm_user")
@ApiModel(description="自媒体用户")
public class WmUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 登录用户名
     */
    @ApiModelProperty(value = "登录用户名")
    @TableField("name")
    private String name;

    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    @TableField("password")
    private String password;

    /**
     * 盐
     */
    @ApiModelProperty(value = "盐")
    @TableField("salt")
    private String salt;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @TableField("nickname")
    private String nickname;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    /**
     * 最后一次登录时间
     */
    @ApiModelProperty(value = "最后一次登录时间")
    @TableField("login_time")
    private Date loginTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;

    @ApiModelProperty(value = "是否有效")
    @TableField("status")
    private Integer status;

}
