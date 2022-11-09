package com.news.admin.feign;

import com.cl.common.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Service
@FeignClient("news-article")
public interface NewsFeign {
    @DeleteMapping("/api/v1/article/{id}")
    public ResponseResult deletenews(@PathVariable("id") Long id);
}
