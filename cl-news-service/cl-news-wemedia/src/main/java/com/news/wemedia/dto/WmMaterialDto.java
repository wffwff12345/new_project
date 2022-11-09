package com.news.wemedia.dto;

import com.cl.common.dto.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WmMaterialDto extends PageRequest {
    Integer isCollection; //1 查询收藏的
}