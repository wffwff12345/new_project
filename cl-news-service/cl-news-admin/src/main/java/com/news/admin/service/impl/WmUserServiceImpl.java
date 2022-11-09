package com.news.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cl.common.dto.PageResponseResult;
import com.cl.common.dto.ResponseResult;
import com.cl.common.enums.AppHttpCodeEnum;
import com.news.admin.dto.ManageUserDto;
import com.news.admin.dto.WmRegisterOrEditorDto;
import com.news.admin.entity.ApUser;
import com.news.admin.entity.WmUser;
import com.news.admin.mapper.WmUserMapper;
import com.news.admin.service.IWmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 自媒体用户信息表 服务实现类
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements IWmUserService {

    @Override
    public ResponseResult deleteuser(Integer id) {
        WmUser wmUser = this.getById(id);
        if (wmUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        this.removeById(id);
        return ResponseResult.okResult();
    }
    public ResponseResult AddUser(WmRegisterOrEditorDto dto) {
        if(StringUtils.isEmpty(dto.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NAME_ISEMPTY);
        }
        LambdaQueryWrapper<WmUser> query=new LambdaQueryWrapper<>();
        query.eq(WmUser::getName,dto.getName());
        WmUser one = this.getOne(query);
        if(one!=null){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_EXIST);
        }
        WmUser wmUser = new WmUser();
        String password = dto.getPassword();
        wmUser.setSalt("123");
        String password1=password+wmUser.getSalt();
        String md5psw = DigestUtils.md5DigestAsHex(password1.getBytes());
        wmUser.setPassword(md5psw);
        wmUser.setName(dto.getName());
        wmUser.setNickname(dto.getNickname());
        wmUser.setPhone(dto.getPhone());
        wmUser.setEmail(dto.getEmail());
        wmUser.setCreatedTime(new Date());
        wmUser.setStatus(1);
        this.save(wmUser);
        String s="用户注册成功!";
        System.out.println();
        return ResponseResult.okResult(s);
    }
    @Override
    public ResponseResult ListByName(ManageUserDto dto) {
        IPage<WmUser> page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmUser> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(dto.getName())) {
            queryWrapper.like(WmUser::getName, dto.getName());
        }
        IPage<WmUser> page1 = this.page(page, queryWrapper);
        long total = page1.getTotal();
        List<WmUser> records = page1.getRecords();
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), total, records);
        return result;
    }

    public ResponseResult upadtestatus(Integer id, Integer type) {
        WmUser wmUser = this.getById(id);
        if (wmUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        LambdaUpdateWrapper<WmUser> update=new LambdaUpdateWrapper<>();
        update.eq(WmUser::getId,id);
        update.set(WmUser::getStatus,type);
        this.update(update);
        return ResponseResult.okResult();
    }
}