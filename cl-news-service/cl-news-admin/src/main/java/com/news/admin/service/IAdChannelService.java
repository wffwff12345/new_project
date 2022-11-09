package com.news.admin.service;

import com.cl.common.dto.ResponseResult;
import com.news.admin.dto.ChannelDto;
import com.news.admin.entity.AdChannel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IAdChannelService extends IService<AdChannel> {
    ResponseResult insertchannel(AdChannel entity);

    ResponseResult deletechannel(Long id);

    ResponseResult updatechannel(AdChannel entity);

    ResponseResult ListByName(ChannelDto dto);

    ResponseResult lists();
}
