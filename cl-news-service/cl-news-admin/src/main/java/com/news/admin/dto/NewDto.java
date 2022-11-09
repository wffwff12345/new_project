package com.news.admin.dto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cl.common.dto.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewDto extends PageRequest{

    private String title;

}
