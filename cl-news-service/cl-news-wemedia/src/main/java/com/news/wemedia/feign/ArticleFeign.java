package com.news.wemedia.feign;

import com.cl.common.dto.ResponseResult;
import com.news.wemedia.dto.ArticleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(value = "news-article")
public interface ArticleFeign {


    @PostMapping("/api/v1/article")
    public ResponseResult<Long> saveArticle(@RequestBody ArticleDto dto);
}