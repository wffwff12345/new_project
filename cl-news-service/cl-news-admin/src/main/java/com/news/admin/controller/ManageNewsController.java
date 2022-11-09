package com.news.admin.controller;

import com.cl.common.dto.ResponseResult;
import com.news.admin.dto.NewDto;
import com.news.admin.service.IWmNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/managenews")
@Api(tags = "管理新闻信息")
public class ManageNewsController {
    @Autowired
    private IWmNewsService NewsService;
    @PostMapping("/list")
    public ResponseResult listByCondition(@RequestBody NewDto dto){
        ResponseResult responseResult = NewsService.listByCondition(dto);
        return responseResult;
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable("id") Integer id){
        return NewsService.deleteById(id);

    }
    @GetMapping("/{id}")
    @ApiOperation(value = "查找新闻",notes ="" )
    @ApiImplicitParam(name = "id",value = "文章Id",required = true,dataType = "Integer")
    public ResponseResult getById(@PathVariable("id") Integer id) {
        return NewsService.getOneById(id);
    }
}
