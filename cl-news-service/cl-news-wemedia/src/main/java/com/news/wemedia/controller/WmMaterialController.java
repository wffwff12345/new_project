package com.news.wemedia.controller;

import com.cl.common.dto.ResponseResult;
import com.news.wemedia.dto.WmMaterialDto;
import com.news.wemedia.service.IWmMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
@Api(tags = "自媒体图文素材信息")
public class WmMaterialController {
     @Autowired
     private IWmMaterialService wmMaterialService;
    @PostMapping("/upload_picture")
    @ApiOperation(value = "上传图片",notes ="" )
    @ApiImplicitParam(name = "file",value = "图片",required = true,dataType = "MultipartFile")
    public ResponseResult upload(MultipartFile file) {
        return wmMaterialService.upload(file);
    }

    @PostMapping("/list")
    @ApiOperation(value = "收藏展示",notes ="" )
    @ApiImplicitParam(name = "dto",value = "图片",required = true,dataType = "WmMaterialDto")
    public ResponseResult list(@RequestBody WmMaterialDto dto){
        return wmMaterialService.listByCollection(dto);
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除图片",notes ="" )
    @ApiImplicitParam(name = "id",value = "图片Id",required = true,dataType = "Integer")
    public ResponseResult deleteById(@PathVariable("id") Integer id) {
        return wmMaterialService.deleteById(id);
    }
    @PutMapping("/collect/{id}")
    @ApiOperation(value = "收藏",notes ="" )
    @ApiImplicitParam(name = "id",value = "图片Id",required = true,dataType = "Integer")
    public ResponseResult collect(@PathVariable("id") Integer id) {
        return wmMaterialService.updateStatus(id, 1);
    }
    @PutMapping("/cancel_collect/{id}")
    @ApiOperation(value = "取消收藏",notes ="" )
    @ApiImplicitParam(name = "id",value = "图片Id",required = true,dataType = "Integer")
    public ResponseResult cancelCollect(@PathVariable("id") Integer id) {
        return wmMaterialService.updateStatus(id, 0);
    }
}
