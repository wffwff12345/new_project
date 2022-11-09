package com.news.admin.controller;

import com.cl.common.dto.ResponseResult;
import com.news.admin.dto.AdRejisterUser;
import com.news.admin.dto.AdUserDto;
import com.news.admin.entity.AdUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.news.admin.service.IAdUserService;

import java.util.Date;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 管理员用户信息表 前端控制器
 * </p>
 *
 * @author mcm
 * @since 2022-03-16
 */
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "管理员用户信息")
public class AdUserController{

    @Autowired
    private IAdUserService adUserService;

    @PostMapping(value = "/register")
    @ApiOperation(value = "用户注册",notes ="" )
    @ApiImplicitParam(name = "dto",value = "注册用户",required = true,dataType = "dto")
    public ResponseResult register(@RequestBody AdRejisterUser dto){
        return adUserService.register(dto);
    }
    @PostMapping("/editor")
    @ApiOperation(value = "修改信息",notes ="" )
    @ApiImplicitParam(name = "dto",value = "修改信息",required = true,dataType = "AdRejisterUser")
    public ResponseResult editor(@RequestBody AdRejisterUser dto){
        return adUserService.editor(dto);
    }
    @PostMapping("/login")
    @ApiOperation(value="用户登录",notes = "")
    @ApiImplicitParam(name = "dto",value = "用户登录",required = true,dataType = "AdUserDto")
    // public ResponseResult login(@RequestBody AdUserDto dto){

    //     return adUserService.login(dto);
    // }
    public ResponseResult login( String name,String password){
        HashMap<String,Object> map=new HashMap<>();
        map.put("name", name);
        map.put("password", password);
        return ResponseResult.okResult(map);
    }
    @PostMapping("/get")
    @ApiOperation(value = "获取用户",notes ="" )
    @ApiImplicitParam(name = "name",value = "获取用户",required = true,dataType = "name")
    public ResponseResult get(@RequestBody AdRejisterUser dto){
        return adUserService.get(dto);
    }
}
