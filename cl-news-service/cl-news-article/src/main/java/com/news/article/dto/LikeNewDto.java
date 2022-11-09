package com.news.article.dto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cl.common.dto.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LikeNewDto extends PageRequest {

}
