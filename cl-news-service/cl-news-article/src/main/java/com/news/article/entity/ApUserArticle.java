package com.news.article.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 管理文章信息表
 * </p>
 *
 * @author mcm
 * @since 2022-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap__user_article")
@ApiModel(description="管理文章信息表")
public class ApUserArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 管理员用户ID
     */
    @ApiModelProperty(value = "用户ID")
    @TableField("ap_id")
    private Integer apId;

    /**
     * ArticleID
     */
    @ApiModelProperty(value = "ArticleID")
    @TableField("article_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;
    /**
     * 评论
     */
    @ApiModelProperty(value = "评论")
    @TableField("comments")
    private String comments;


}
