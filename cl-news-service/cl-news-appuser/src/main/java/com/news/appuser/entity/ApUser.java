package com.news.appuser.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * APP用户信息表
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap_user")
@ApiModel(description="APP用户信息表")

public class ApUser implements Serializable {


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 密码、通信等加密盐
     */
    @ApiModelProperty(value = "密码、通信等加密盐")
    @TableField("salt")
    private String salt;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField("name")
    private String name;

    /**
     * 密码,md5加密
     */
    @ApiModelProperty(value = "密码,md5加密")
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @TableField("sex")
    private String sex;

    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间")
    @TableField("created_time")
    private Date createdTime;

    @ApiModelProperty(value = "是否有效")
    private Integer status;
}
