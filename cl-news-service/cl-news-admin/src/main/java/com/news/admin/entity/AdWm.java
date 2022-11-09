package com.news.admin.entity;

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
 * 管理自媒体用户信息表
 * </p>
 *
 * @author mcm
 * @since 2022-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ad_wm")
@ApiModel(description="管理自媒体用户信息表")
public class AdWm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 管理员用户ID
     */
    @ApiModelProperty(value = "管理员用户ID")
    @TableField("ad_id")
    private Integer adId;

    /**
     * 自媒体用户ID
     */
    @ApiModelProperty(value = "自媒体用户ID")
    @TableField("wm_id")
    private Integer wmId;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    @TableField("operate_time")
    private Date operateTime;


}
