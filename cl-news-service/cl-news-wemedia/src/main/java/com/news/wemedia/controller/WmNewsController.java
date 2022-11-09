package com.news.wemedia.controller;

import com.cl.common.dto.ResponseResult;
import com.news.wemedia.dto.WmNewsDto;
import com.news.wemedia.dto.WmNewsPageDto;
import com.news.wemedia.entity.WmNews;
import com.news.wemedia.service.IWmNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 自媒体图文内容信息表 前端控制器
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/api/v1/news")
@Api(tags = "自媒体图文内容信息表接口")
@CrossOrigin
public class WmNewsController{

    @Autowired
    private IWmNewsService wmNewsService;
    @ApiOperation(value = "新闻展示",notes ="" )
    @ApiImplicitParam(name = "dto",value = "图片",required = true,dataType = "WmNewsPageDto")
    @PostMapping("/list")
    public ResponseResult listByCondition(@RequestBody WmNewsPageDto dto) {
        return wmNewsService.listByCondition(dto);}
    @ApiOperation(value = "发布新闻",notes ="" )
    @ApiImplicitParam(name = "dto",value = "文章",required = true,dataType = "WmNewsDto")
    @PostMapping("/submit")
     public ResponseResult submit(@RequestBody WmNewsDto dto){
        return wmNewsService.submit(dto);
        }
    @GetMapping("/{id}")
    @ApiOperation(value = "查找新闻",notes ="" )
    @ApiImplicitParam(name = "id",value = "文章Id",required = true,dataType = "Integer")
    public ResponseResult getById(@PathVariable("id") Integer id) {
        return wmNewsService.getOneById(id);
    }
    @ApiOperation(value = "删除新闻",notes ="" )
    @ApiImplicitParam(name = "id",value = "文章Id",required = true,dataType = "Integer")
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable("id") Integer id) {
        return wmNewsService.deleteById(id);
    }
}