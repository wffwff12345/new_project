package com.news.wemedia.feign;

import com.cl.common.dto.ResponseResult;
import com.news.wemedia.dto.AdChannel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient("news-admin")
public interface AdminFeign {

    @GetMapping("/api/v1/channel/{id}")
    public ResponseResult<AdChannel> getChannelById(@PathVariable("id") Integer id);
}