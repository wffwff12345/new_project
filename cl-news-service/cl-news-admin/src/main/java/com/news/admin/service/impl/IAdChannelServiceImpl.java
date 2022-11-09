package com.news.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cl.common.dto.PageResponseResult;
import com.cl.common.dto.ResponseResult;
import com.cl.common.enums.AppHttpCodeEnum;
import com.news.admin.dto.ChannelDto;
import com.news.admin.entity.AdChannel;
import com.news.admin.mapper.AdchannelMapper;
import com.news.admin.service.IAdChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class IAdChannelServiceImpl extends ServiceImpl<AdchannelMapper, AdChannel> implements IAdChannelService {
    @Override
    public ResponseResult insertchannel(AdChannel entity) {

        if (StringUtils.isEmpty(entity.getName()) || entity == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        }

        LambdaQueryWrapper<AdChannel> query = new LambdaQueryWrapper<>();
        query.eq(AdChannel::getName, entity.getName());
        AdChannel one = this.getOne(query);
        if (one != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }
        this.save(entity);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deletechannel(Long id) {
        AdChannel channel = this.getById(id);
        if (channel == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        if (channel.getStatus()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_CAN_NOT_DELETE);
        }

        this.removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updatechannel(AdChannel entity) {
        if (StringUtils.isEmpty(entity.getName())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        LambdaQueryWrapper<AdChannel> query = new LambdaQueryWrapper<>();
        query.eq(AdChannel::getName, entity.getName());
        List<AdChannel> list = this.list(query);
        if(list.size()>=1){
            for (AdChannel channel : list) {
                String description = channel.getDescription();
                String description1 = entity.getDescription();
                Boolean status = entity.getStatus();
                Boolean status1 = channel.getStatus();
                if (description1.equals(description)&&status==status1){
                    return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
                }

            }
            this.updateById(entity);
            return ResponseResult.okResult();
        }else {
            this.updateById(entity);
            return ResponseResult.okResult();
        }
    }

    @Override
    public ResponseResult ListByName(ChannelDto dto) {

        IPage<AdChannel> page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<AdChannel> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(dto.getName())) {
            queryWrapper.like(AdChannel::getName, dto.getName());
        }
        IPage<AdChannel> page1 = this.page(page, queryWrapper);
        long total = page1.getTotal();
        List<AdChannel> records = page1.getRecords();
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), total, records);
        return result;

    }

    @Override
    public ResponseResult lists() {
        LambdaUpdateWrapper<AdChannel> query=new LambdaUpdateWrapper<>();
        query.eq(AdChannel::getStatus,1);
        List<AdChannel> list = this.list(query);
        return ResponseResult.okResult(list);
    }

}