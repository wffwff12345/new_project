package com.news.admin.controller;

import com.cl.common.dto.ResponseResult;
import com.news.admin.dto.ManageUserDto;

import com.news.admin.dto.WmRegisterOrEditorDto;
import com.news.admin.service.IWmUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/managewemedia")
@Api(tags = "管理自媒体用户信息")
public class ManageWemediaUserController {
    @Autowired
    private IWmUserService wmUserService;
    @PostMapping("/list")
    @ApiOperation(value = "查询用户", notes = " ")
    @ApiImplicitParam(name = "dto", value = "查询对象", required = true, dataType = "ManageUserDto")
    public ResponseResult list(@RequestBody ManageUserDto dto) {
        return wmUserService.ListByName(dto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户", notes = "")
    @ApiImplicitParam(name = "id", value = "删除序号", required = true, dataType = "Long")
    public ResponseResult deleteuser(@PathVariable("id") Integer id) {
        return wmUserService.deleteuser(id);

    }
    @PostMapping("/user_add")
    @ApiOperation(value = "查询用户", notes = " ")
    @ApiImplicitParam(name = "dto", value = "增加对象", required = true, dataType = "WmRegisterOrEditorDto")
    public ResponseResult list(@RequestBody WmRegisterOrEditorDto dto) {
        return wmUserService.AddUser(dto);
    }
    @PutMapping("/valid_status/{id}")
    @ApiOperation(value = "有效状态",notes ="" )
    @ApiImplicitParam(name = "id",value = "WdId",required = true,dataType = "Integer")
    public ResponseResult validstatus(@PathVariable("id") Integer id) {
        return wmUserService.upadtestatus(id,1);
    }
    @PutMapping("/invalid_status/{id}")
    @ApiOperation(value = "无效状态",notes ="" )
    @ApiImplicitParam(name = "id",value = "WdId",required = true,dataType = "Integer")
    public ResponseResult invalidstatus(@PathVariable("id") Integer id) {
        return wmUserService.upadtestatus(id,0);
    }
}