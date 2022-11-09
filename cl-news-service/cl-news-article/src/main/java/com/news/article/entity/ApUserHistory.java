package com.news.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap__user_history")
@ApiModel(description="管理文章信息表")
@AllArgsConstructor
@NoArgsConstructor
public class ApUserHistory implements Serializable {

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

        @ApiModelProperty(value = "浏览时间")
        @TableField("look_time")
        private Date lookTime;
    }

