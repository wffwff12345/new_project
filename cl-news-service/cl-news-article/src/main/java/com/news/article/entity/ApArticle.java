package com.news.article.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章信息表，存储已发布的文章
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)//链式访问
@TableName("ap_article")
@ApiModel(description="文章信息表，存储已发布的文章")
public class ApArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;


    @ApiModelProperty(value = "作者昵称")
    @TableField("author_name")
    private String authorName;

    /**
     * 文章所属频道ID
     */
    @ApiModelProperty(value = "文章所属频道ID")
    @TableField("channel_id")
    private Integer channelId;

    /**
     * 频道名称
     */
    @ApiModelProperty(value = "频道名称")
    @TableField("channel_name")
    private String channelName;


    @ApiModelProperty(value = "文章布局")
    @TableField("layout")
    private Integer layout;

    @ApiModelProperty(value = "文章图片")
    @TableField("images")
    private String images;


    @ApiModelProperty(value = "文章标签")
    @TableField("labels")
    private String labels;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @TableField("publish_time")
    private Date publishTime;




}
