package com.news.wemedia.controller;

import com.news.wemedia.service.IWmNewsMaterialService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 自媒体图文引用素材信息表 前端控制器
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/api/v1/news_material")
@Api(tags = "自媒体图文引用素材信息表接口")
@CrossOrigin
public class WmNewsMaterialController{

    @Autowired
    private IWmNewsMaterialService wmNewsMaterialService;


}
