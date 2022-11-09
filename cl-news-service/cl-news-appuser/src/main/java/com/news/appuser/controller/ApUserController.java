package com.news.appuser.controller;

import com.cl.common.dto.ResponseResult;
import com.news.appuser.dto.ApRegisterDto;
import com.news.appuser.dto.LoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.news.appuser.service.IApUserService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * APP用户信息表 前端控制器
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "APP用户信息表接口")
@CrossOrigin
public class ApUserController{

    @Autowired
    private IApUserService apUserService;
    @PostMapping("/register")
    @ApiOperation(value = "用户注册",notes ="" )
    @ApiImplicitParam(name = "dto",value = "注册用户",required = true,dataType = "dto")
    public ResponseResult register(@RequestBody ApRegisterDto dto){
        return apUserService.register(dto);
    }
    @PostMapping("/editor")
    @ApiOperation(value = "用户编辑",notes ="" )
    @ApiImplicitParam(name = "dto",value = "用户编辑",required = true,dataType = "dto")
    public ResponseResult editor(@RequestBody ApRegisterDto dto){
        return apUserService.editor(dto);
    }
    @PostMapping("/login")
    @ApiOperation(value = "登录",notes ="" )
    @ApiImplicitParam(name = "dto",value = "登录",required = true,dataType = "LoginDto")
    public ResponseResult in(@RequestBody LoginDto dto) {
        return apUserService.login(dto);
    }
    @PostMapping("/get")
    @ApiOperation(value = "获取用户",notes ="" )
    @ApiImplicitParam(name = "name",value = "获取用户",required = true,dataType = "name")
    public ResponseResult get(@RequestBody ApRegisterDto dto){
        return apUserService.get(dto);
    }
}
