package com.news.article.controller;

import com.cl.common.dto.ResponseResult;
import com.news.article.dto.CommentsDto;
import com.news.article.dto.FocusDto;
import com.news.article.service.IApUserArticlefocusService;
import com.news.article.service.IApUserArticlelikesService;
import com.news.article.service.IApUserHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.news.article.service.IApUserArticleService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 管理文章信息表 前端控制器
 * </p>
 *
 * @author mcm
 * @since 2022-03-28
 */
@RestController
@RequestMapping("/api/v1/user_article")
@Api(tags = "管理文章信息表接口")
@CrossOrigin
public class ApUserArticleController{

    @Autowired
    private IApUserArticleService apUserArticleService;
    @Autowired
    private IApUserArticlelikesService iApUserArticlelikesService;
    @Autowired
    private IApUserArticlefocusService apUserArticlefocusService;
    @Autowired
    private IApUserHistoryService apUserHistoryService;
    @PostMapping("/app_user")
    @ApiOperation(value = "",notes ="" )
    @ApiImplicitParam(name = "Long",value = "",required = true,dataType = "Long")
    public ResponseResult loadappuser(@RequestBody Long id){
        return iApUserArticlelikesService.loadappuser(id);
    }
    @PostMapping("/addapp_user")
    @ApiOperation(value = "",notes ="" )
    @ApiImplicitParam(name = "Long",value = "",required = true,dataType = "Long")
    public ResponseResult addappuser(@RequestBody Long id){
        return iApUserArticlelikesService.addappuser(id);
    }
    @PostMapping("/like/{id}")
    @ApiOperation(value = "喜欢",notes ="" )
    @ApiImplicitParam(name = "id",value = "新闻Id",required = true,dataType = "Long")
    public ResponseResult like(@PathVariable("id") Long id) {
        return iApUserArticlelikesService.updateStatus(id, 1);
    }
    @PostMapping("/cancel_like/{id}")
    @ApiOperation(value = "取消",notes ="" )
    @ApiImplicitParam(name = "id",value = "新闻Id",required = true,dataType = "Long")
    public ResponseResult cancellike(@PathVariable("id") Long id) {
        return iApUserArticlelikesService.updateStatus(id, 0);
    }
    @PostMapping("/number")
    @ApiOperation(value = "数量",notes ="" )
    public ResponseResult number() {
        return iApUserArticlelikesService.number();
    }
    @PostMapping("/app_userfocus")
    @ApiOperation(value = "",notes ="" )
    @ApiImplicitParam(name = "Lone",value = "",required = true,dataType = "Long")
    public ResponseResult loadappfocus(@RequestBody Long id){
        return apUserArticlefocusService.loadappuser(id);
    }
    @PostMapping("/addapp_userfocus")
    @ApiOperation(value = "",notes ="" )
    @ApiImplicitParam(name = "Long",value = "",required = true,dataType = "Long")
    public ResponseResult addappfocus(@RequestBody Long id){
        return apUserArticlefocusService.addappuser(id);
    }
    @PostMapping("/focus/{id}")
    @ApiOperation(value = "关注",notes ="" )
    @ApiImplicitParam(name = "id",value = "FocusDto",required = true,dataType = "Long")
    public ResponseResult focus(@PathVariable("id") Long id) {
        return apUserArticlefocusService.updateStatus(id, 1);
    }
    @PostMapping("/cancel_focus/{id}")
    @ApiOperation(value = "取消关注",notes ="" )
    @ApiImplicitParam(name = "FocusDto",value = "FocusDto",required = true,dataType = "FocusDto")
    public ResponseResult cancelfocus(@PathVariable("id") Long id) {
        return apUserArticlefocusService.updateStatus(id, 0);
    }
    @PostMapping("/focusnumber")
    @ApiOperation(value = "关注数量",notes ="" )
    @ApiImplicitParam(name = "number",value = "number",required = true,dataType = "number")
    public ResponseResult focusnumber() {
        return apUserArticlefocusService.focusnumber();
    }
   @PostMapping("/comments")
   @ApiOperation(value = "评论",notes = "")
   @ApiImplicitParam(name = "dto",value = "dto",required = true,dataType = "CommentsDto")
    public ResponseResult comments(@RequestBody CommentsDto dto){
        return apUserArticleService.comments(dto);
   }
   @PostMapping("/add_history")
   @ApiOperation(value = "",notes ="" )
   @ApiImplicitParam(name = "Long",value = "",required = true,dataType = "Long")
   public ResponseResult addhistory(@RequestBody Long id){
        return apUserHistoryService.addhistory(id);
    }
    @DeleteMapping("/delete_history/{id}")
    @ApiOperation(value = "",notes ="" )
    @ApiImplicitParam(name = "Long",value = "",required = true,dataType = "Long")
    public ResponseResult deletehistory(@PathVariable("id") Long id){
        return apUserHistoryService.deletehistory(id);
    }
}
