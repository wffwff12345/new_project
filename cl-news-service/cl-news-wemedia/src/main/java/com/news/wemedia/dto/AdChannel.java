package com.news.wemedia.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ad_channel")
public class AdChannel {
    @ApiModelProperty(value = "频道ID")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "频道名称")
    private String name;
    @ApiModelProperty(value = "频道描述")
    private String description;
    @ApiModelProperty(value = "是否默认")
    private Boolean isDefault;
    @ApiModelProperty(value = "是否有效")
    private Boolean status;
    @ApiModelProperty(value = "频道排序")
    private Integer ord;
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}
