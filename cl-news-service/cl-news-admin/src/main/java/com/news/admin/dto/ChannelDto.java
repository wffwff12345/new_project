package com.news.admin.dto;
import com.cl.common.dto.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelDto extends PageRequest{
    @ApiModelProperty(value = "频道名称")
    private String name;
    private Integer status;
}
