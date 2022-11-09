package com.news.admin.dto;
import com.cl.common.dto.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManageUserDto extends PageRequest{
    @ApiModelProperty(value = "用户名称")
    private String name;
}
