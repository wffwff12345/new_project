package com.news.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName("ad_channel")
public class AdChannel implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "频道ID")
    private Integer id;
    @ApiModelProperty(value = "频道名称")
    private String name;
    @ApiModelProperty(value = "频道描述")
    private String description;
    @ApiModelProperty(value = "是否有效")
    private Boolean status;
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}
