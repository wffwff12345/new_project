package com.news.article.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cl.common.dto.ResponseResult;
import com.news.article.dto.*;
import com.news.article.entity.ApArticle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.news.article.service.IApArticleService;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文章信息表，存储已发布的文章 前端控制器
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/api/v1/article")
@Api(tags = "新闻信息表，存储已发布的新闻接口")
@CrossOrigin
public class ApArticleController{

    @Autowired
    private IApArticleService apArticleService;
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除新闻",notes ="" )
    @ApiImplicitParam(name = "id",value = "删除序号",required = true,dataType = "Long")
    public ResponseResult deletenews(@PathVariable("id")Long id){
        return apArticleService.deletenews(id);
    }
    @PostMapping("/list")
    @ApiOperation(value = "",notes = "")
    @ApiImplicitParam(name = "查询新闻",value ="新闻" ,required = true,dataType = "NewDto")
    public ResponseResult listByCondition(@RequestBody NewDto dto){
        ResponseResult responseResult = apArticleService.listByCondition(dto);
        return responseResult;
    }
    @PostMapping("/byId/{id}")
    @ApiOperation(value = "",notes = "")
    @ApiImplicitParam(name = "查询新闻",value ="新闻" ,required = true,dataType = "Long")
    public ResponseResult getById(@PathVariable("id") Long id){
        ApArticle article = apArticleService.getById(id);
        return ResponseResult.okResult(article);
    }
    @PostMapping
    @ApiOperation(value = "保存新闻",notes ="" )
    @ApiImplicitParam(name = "dto",value = "新闻",required = true,dataType = "ArticleDto")
    public ResponseResult<Long> savaArticle(@RequestBody ArticleDto dto){
        return apArticleService.saveArticle(dto);
    }
    @PostMapping("/load")
    @ApiOperation(value = "加载新闻",notes ="" )
    @ApiImplicitParam(name = "dto",value = "新闻",required = true,dataType = "ArticleHomeDto")
    public ResponseResult loadArticle(@RequestBody ArticleHomeDto dto) {
        return apArticleService.loadArticle(dto, 1);
    }
    /**
     * 上拉获取更多文章
     *
     * @param dto
     * @return
     */
    @PostMapping("/loadmore")
    @ApiOperation(value = "加载更多新闻",notes ="" )
    @ApiImplicitParam(name = "dto",value = "新闻",required = true,dataType = "ArticleHomeDto")
    public ResponseResult loadMore(@RequestBody ArticleHomeDto dto) {
        return apArticleService.loadArticle(dto, 1);
    }

    @PostMapping("/loadnew")
    @ApiOperation(value = "加载最新新闻",notes ="" )
    @ApiImplicitParam(name = "dto",value = "新闻",required = true,dataType = "ArticleHomeDto")
    public ResponseResult loadNew(@RequestBody ArticleHomeDto dto) {
        return apArticleService.loadArticle(dto, 0);
    }
    @PostMapping("/load_article_info")
    @ApiOperation(value = "查看新闻详情",notes ="" )
    @ApiImplicitParam(name = "Long",value = "新闻",required = true,dataType = "Long")
    public ResponseResult loadArticleInfo(@RequestBody Long id){
        return apArticleService.loadArticleInfo(id);
    }
    /*@PostMapping("/foucs")
    @ApiOperation(value = "查看关注作者的新闻",notes ="" )
    @ApiImplicitParam(name = "Long",value = "新闻",required = true,dataType = "Long")
    public ResponseResult loadArticleInfo(@RequestBody Long id){
        return apArticleService.loadArticleInfo(id);
    }*/
    @PostMapping("/like_list")
    @ApiOperation(value = "查看喜欢新闻",notes ="" )
    @ApiImplicitParam(name = "查询新闻",value ="新闻" ,required = true,dataType = "LikeNewDto")
    public ResponseResult listBylike(@RequestBody LikeNewDto dto){
        ResponseResult responseResult = apArticleService.loadlikelist(dto);
        return responseResult;
    }
    @PostMapping("/focus_list")
    @ApiOperation(value = "查看关注的作者新闻",notes ="" )
    @ApiImplicitParam(name = "查询新闻",value ="新闻" ,required = true,dataType = "FocusNewDto")
    public ResponseResult listByfocus(@RequestBody FocusNewDto dto){
        ResponseResult responseResult = apArticleService.loadfocuslist(dto);
        return responseResult;
    }
    @PostMapping("/list_by_name")
    @ApiOperation(value = "",notes = "")
    @ApiImplicitParam(name = "查询新闻",value ="新闻" ,required = true,dataType = "NewDto")
    public ResponseResult listByCondition(@RequestBody   NameDto dto){
        ResponseResult responseResult = apArticleService.listByName(dto);
        return responseResult;
    }
    @PostMapping("/history_list")
    @ApiOperation(value = "查看历史新闻",notes ="" )
    @ApiImplicitParam(name = "查询新闻",value ="新闻" ,required = true,dataType = "FocusNewDto")
    public ResponseResult loadhistory(@RequestBody HistoryDto dto){
        ResponseResult responseResult = apArticleService.loadhistory(dto);
        return responseResult;
    }
}



