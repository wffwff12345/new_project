package com.news.admin.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.AppJwtUtil;
import com.cl.common.util.WmThreadLocal;
import com.news.admin.dto.AdRejisterUser;
import com.news.admin.dto.AdUserDto;
import com.news.admin.entity.AdUser;
import com.news.admin.mapper.AdUserMapper;
import com.news.admin.service.IAdUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 * 管理员用户信息表 服务实现类
 * </p>
 *
 * @author mcm
 * @since 2022-03-16
 */
@Service
public class AdUserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements IAdUserService {

    @Override
    public ResponseResult login(AdUserDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NAME_ISEMPTY);
        }
        LambdaQueryWrapper<AdUser> query = new LambdaQueryWrapper<>();
        query.eq(AdUser::getName, dto.getName());
        AdUser one = this.getOne(query);
        if (one == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_DATA_NOT_EXIST);

        }
        String salt=one.getSalt();
        String psw=dto.getPassword()+salt;
        String md5psw= DigestUtils.md5DigestAsHex(psw.getBytes());
        if(!md5psw.equals(one.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        LambdaUpdateWrapper<AdUser> update=new LambdaUpdateWrapper<>();
        update.eq(AdUser::getName,dto.getName());
        update.set(AdUser::getLoginTime,new Date());
        this.update(update);
        String token= AppJwtUtil.getToken(one.getId().longValue());
        HashMap<String, Object> map = new HashMap<>();
        one.setLoginTime(new Date());
        one.setPassword("******");
        one.setSalt("******");
        one.setPhone("*******");
        map.put("user",one);
        map.put("token",token);
        return ResponseResult.okResult(map);
    }
    @Override
    public ResponseResult register( AdRejisterUser dto) {
        if(StringUtils.isEmpty(dto.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NAME_ISEMPTY);
        }
       LambdaQueryWrapper<AdUser> query=new LambdaQueryWrapper<>();
        query.eq(AdUser::getName,dto.getName());
        AdUser one = this.getOne(query);
        if(one!=null){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_EXIST);
        }
        AdUser user = new AdUser();
        String password = dto.getPassword();
        user.setSalt("123");
        String password1=password+user.getSalt();
        String md5psw = DigestUtils.md5DigestAsHex(password1.getBytes());
        user.setPassword(md5psw);
        user.setName(dto.getName());
        user.setNickname(dto.getNickname());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setCreatedTime(new Date());
        this.save(user);
        String s="用户注册成功!";
        return ResponseResult.okResult(s);
    }

    @Override
    public ResponseResult editor(AdRejisterUser dto) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaQueryWrapper<AdUser> query=new LambdaQueryWrapper<>();
        query.eq(AdUser::getName,dto.getName());
        AdUser one = this.getOne(query);
        if (one==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
        }
        String salt = one.getSalt();
        String pwd = dto.getPassword() + salt;
        String md5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
        LambdaUpdateWrapper<AdUser> update = new LambdaUpdateWrapper<>();
        update.eq(AdUser::getName,dto.getName());
        update.set(AdUser::getPassword,md5);
        update.set(AdUser::getPhone, dto.getPhone());
        update.set(AdUser::getEmail,dto.getEmail());
        update.set(AdUser::getNickname,dto.getNickname());
        boolean update1 = this.update(update);
        System.out.println();
        if (update1==true){
            return ResponseResult.okResult();
        }return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }

    @Override
    public ResponseResult get(AdRejisterUser dto) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        if(StringUtils.isEmpty(dto.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_CAN_NOT_EMPTY);
        }
        LambdaQueryWrapper<AdUser> query2=new LambdaQueryWrapper<>();
        query2.eq(AdUser::getName,dto.getName());
        AdUser adUser = this.getOne(query2);
        LambdaQueryWrapper<AdUser> query=new LambdaQueryWrapper<>();
        query.eq(AdUser::getName,dto.getName());
        String password = dto.getPassword();
        String salt = adUser.getSalt();
        password=password+salt;
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        if (s.equals(adUser.getPassword())){
            return ResponseResult.okResult("TRUE");
        }
        return ResponseResult.okResult("false");
    }

}
