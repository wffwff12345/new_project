package com.news.admin.dto;

import com.cl.common.dto.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SensitiveDto extends PageRequest {
    private String name;
}
