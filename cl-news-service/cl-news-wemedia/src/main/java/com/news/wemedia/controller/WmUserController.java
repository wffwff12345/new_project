package com.news.wemedia.controller;


import com.cl.common.dto.ResponseResult;
import com.news.wemedia.dto.WmLoginDto;
import com.news.wemedia.dto.WmRegisterOrEditorDto;
import com.news.wemedia.entity.WmUser;
import com.news.wemedia.service.IWmUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 自媒体用户信息表 前端控制器
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "自媒体用户信息")

public class WmUserController{

    @Autowired
    private IWmUserService wmUserService;
    @PostMapping("/register")
    @ApiOperation(value = "用户注册",notes ="" )
    @ApiImplicitParam(name = "dto",value = "注册用户",required = true,dataType = "WmRegisterDto")
    public ResponseResult register(@RequestBody WmRegisterOrEditorDto dto){
        return wmUserService.register(dto);
    }
    @PostMapping("/editor")
    @ApiOperation(value = "修改用户信息",notes ="" )
    @ApiImplicitParam(name = "dto",value = "修改用户信息",required = true,dataType = "WmRegisterOrEditorDto")
    public ResponseResult editor(@RequestBody WmRegisterOrEditorDto dto){
        return wmUserService.editor(dto);
    }
    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes ="" )
    @ApiImplicitParam(name = "dto",value = "登录用户",required = true,dataType = "WmLoginDto")
    public ResponseResult login(@RequestBody WmLoginDto dto){
        return wmUserService.login(dto);
    }
    @PostMapping("/get")
    @ApiOperation(value = "获取用户",notes ="" )
    @ApiImplicitParam(name = "name",value = "获取用户",required = true,dataType = "name")
    public ResponseResult get(@RequestBody WmRegisterOrEditorDto dto){
        return wmUserService.get(dto);
    }
}