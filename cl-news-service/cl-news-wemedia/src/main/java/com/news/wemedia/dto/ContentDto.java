package com.news.wemedia.dto;

import lombok.Data;

@Data
public class ContentDto {
    /**
     * 类型  text 文本  image 图片
     */
    private String type;

    /**
     * 值
     */
    private String value;
}
