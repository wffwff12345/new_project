package com.news.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cl.common.dto.PageResponseResult;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.WmThreadLocal;
import com.news.admin.dto.ApRegisterDto;
import com.news.admin.dto.ManageUserDto;
import com.news.admin.entity.ApUser;
import com.news.admin.entity.WmUser;
import com.news.admin.mapper.ApUserMapper;
import com.news.admin.service.IApUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * APP用户信息表 服务实现类
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements IApUserService {

    public ResponseResult deleteuser(Long id) {
        ApUser apUser = this.getById(id);
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        this.removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult ListByName(ManageUserDto dto) {
        IPage<ApUser> page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<ApUser> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(dto.getName())) {
            queryWrapper.like(ApUser::getName, dto.getName());
        }
        IPage<ApUser> page1 = this.page(page, queryWrapper);
        long total = page1.getTotal();
        List<ApUser> records = page1.getRecords();
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), total, records);
        return result;
    }
    public ResponseResult AddUser(ApRegisterDto dto) {
        if(StringUtils.isEmpty(dto.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NAME_ISEMPTY);
        }
        LambdaQueryWrapper<ApUser> query=new LambdaQueryWrapper<>();
        query.eq(ApUser::getName,dto.getName());
        ApUser one = this.getOne(query);
        if(one!=null){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_EXIST);
        }
        ApUser user = new ApUser();
        String password = dto.getPassword();
        user.setSalt("123");
        String password1=password+user.getSalt();
        String md5psw = DigestUtils.md5DigestAsHex(password1.getBytes());
        user.setPassword(md5psw);
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setCreatedTime(new Date());
        user.setSex(dto.getSex());
        user.setStatus(1);
        this.save(user);
        String s="用户注册成功!";
        return ResponseResult.okResult(s);
    }

    @Override
    public ResponseResult updatestatus(Integer id, Integer type) {
        ApUser apUser = this.getById(id);
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        LambdaUpdateWrapper<ApUser> update=new LambdaUpdateWrapper<>();
        update.eq(ApUser::getId,id);
        update.set(ApUser::getStatus,type);
        this.update(update);
        return ResponseResult.okResult();
    }
}

