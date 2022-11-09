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
 * 自媒体图文内容信息表
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wm_news")
@ApiModel(description="自媒体图文内容信息表")
public class WmNews implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 自媒体用户ID
     */
    @ApiModelProperty(value = "自媒体用户ID")
    @TableField("user_id")
    private Integer userId;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;


    @ApiModelProperty(value = "图文内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "文章布局")
    @TableField("type")
    private Integer type;

    /**
     * 图文频道ID
     */
    @ApiModelProperty(value = "图文频道ID")
    @TableField("channel_id")
    private Integer channelId;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    @TableField("labels")
    private String labels;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;



    @ApiModelProperty(value = "当前状态")
    @TableField("status")
    private Integer status;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @TableField("publish_time")
    private Date publishTime;

    @ApiModelProperty(value = "拒绝理由")
    @TableField("reason")
    private String reason;

    /**
     * 发布库文章ID
     */
    @ApiModelProperty(value = "发布文章ID")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty(value = "封面图片")
    @TableField("images")
    private String images;


}
