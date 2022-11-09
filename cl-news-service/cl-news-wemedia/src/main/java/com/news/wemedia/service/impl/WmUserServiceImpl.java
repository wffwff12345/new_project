package com.news.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.AppJwtUtil;
import com.cl.common.util.WmThreadLocal;
import com.news.wemedia.dto.WmLoginDto;
import com.news.wemedia.dto.WmRegisterOrEditorDto;
import com.news.wemedia.entity.WmUser;
import com.news.wemedia.mapper.WmUserMapper;
import com.news.wemedia.service.IWmUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;

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
    public ResponseResult login(WmLoginDto dto) {
        if(StringUtils.isEmpty(dto.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NAME_ISEMPTY); }
        LambdaQueryWrapper<WmUser> query=new LambdaQueryWrapper<>();
        query.eq(WmUser::getName,dto.getName());
        WmUser one = this.getOne(query);
        if(one==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_DATA_NOT_EXIST); }
        Integer status = one.getStatus();
        if (status==0){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_STATUS); }
        String salt = one.getSalt();
        String psw=dto.getPassword()+salt;
        String md5psw = DigestUtils.md5DigestAsHex(psw.getBytes());
        if(!md5psw.equals(one.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR); }
        LambdaUpdateWrapper<WmUser> update=new LambdaUpdateWrapper<>();
        update.eq(WmUser::getName,dto.getName());
        update.set(WmUser::getLoginTime, new Date());
        this.update(update);
        String token = AppJwtUtil.getToken(one.getId().longValue());
        HashMap<String, Object> map = new HashMap<>();
        one.setLoginTime(new Date());
        one.setPassword("******");
        one.setPhone("*******");
        one.setSalt("******");
        map.put("user",one);
        map.put("token",token);
        return ResponseResult.okResult(map);
    }

    public ResponseResult register(WmRegisterOrEditorDto dto) {
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
        return ResponseResult.okResult(s);
    }

    @Override
    public ResponseResult editor(WmRegisterOrEditorDto dto) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaQueryWrapper<WmUser> query=new LambdaQueryWrapper<>();
        query.eq(WmUser::getName,dto.getName());
        WmUser one = this.getOne(query);
        if (one==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
        }
        String salt = one.getSalt();
        String pwd = dto.getPassword() + salt;
        String md5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
        LambdaUpdateWrapper<WmUser> update = new LambdaUpdateWrapper<>();
        update.eq(WmUser::getName,dto.getName());
        update.set(WmUser::getPassword,md5);
        update.set(WmUser::getPhone, dto.getPhone());
        update.set(WmUser::getEmail,dto.getEmail());
        update.set(WmUser::getNickname,dto.getNickname());
        boolean update1 = this.update(update);
        if (update1==true){
            return ResponseResult.okResult();
        }return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }

    @Override
    public ResponseResult get(WmRegisterOrEditorDto dto) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        if(StringUtils.isEmpty(dto.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_CAN_NOT_EMPTY);
        }
        LambdaQueryWrapper<WmUser> query2=new LambdaQueryWrapper<>();
        query2.eq(WmUser::getName,dto.getName());
        WmUser wmUser = this.getOne(query2);
        String password = dto.getPassword();
        String salt = wmUser.getSalt();
        password=password+salt;
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        if (s.equals(wmUser.getPassword())){
            return ResponseResult.okResult("TRUE");
        }
        return ResponseResult.okResult("false");
    }


}