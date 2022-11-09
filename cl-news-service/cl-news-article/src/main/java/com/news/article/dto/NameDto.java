package com.news.article.dto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cl.common.dto.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NameDto extends PageRequest {
    @ApiModelProperty(value = "名字")
    @TableField("name")
    private String name;
}
