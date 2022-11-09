package com.news.wemedia.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WmNewsDto {
    private Integer id;
    private String title;
    private Integer channelId;
    private String labels;
    private Date publishTime;
    private String content;
    private Integer type;
    private Date submitedTime;
    private Integer status;
    private String reason;
    private List<String> images;
}
