package com.news.admin.controller;

import com.cl.common.dto.ResponseResult;
import com.news.admin.dto.ChannelDto;
import com.news.admin.entity.AdChannel;
import com.news.admin.service.IAdChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/channel")
@Api(tags = "频道管理")
public class ChannelController {
    @Autowired
    private IAdChannelService adChannelService;
    @PostMapping("/insert")
    @ApiOperation(value = "新增频道",notes ="" )
    @ApiImplicitParam(name = "entity",value = "新增对象",required = true,dataType = "AdChannel")
    public ResponseResult insertchannel(@RequestBody AdChannel entity){
        entity.setCreatedTime(new Date());
        return adChannelService.insertchannel(entity);
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除频道",notes ="" )
    @ApiImplicitParam(name = "id",value = "删除序号",required = true,dataType = "Long")
    public ResponseResult deletechannel(@PathVariable("id")Long id){
        return adChannelService.deletechannel(id);

    }
    @PostMapping("/update")
    @ApiOperation(value = "修改频道",notes ="" )
    @ApiImplicitParam(name = "entity",value = "修改对象",required = true,dataType = "AdChannel")
    public ResponseResult upadtecadchnnel(@RequestBody AdChannel entity){
        return adChannelService.updatechannel(entity);
    }
    @PostMapping("/list")
    @ApiOperation(value = "查询频道",notes = " ")
    @ApiImplicitParam(name = "dto",value ="查询对象",required = true,dataType = "channeldto")
    public ResponseResult list(@RequestBody ChannelDto dto)
    {
        return adChannelService.ListByName(dto);
    }

   @GetMapping("/channels")
   public ResponseResult list(){
       return adChannelService.lists();

   }
   @GetMapping("/{id}")
    public ResponseResult<AdChannel> getChannelById(@PathVariable("id") Integer id){
       AdChannel adChannel = adChannelService.getById(id);
       return ResponseResult.okResult(adChannel);
   }
}
