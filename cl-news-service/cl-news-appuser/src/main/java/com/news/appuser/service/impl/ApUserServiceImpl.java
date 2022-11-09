package com.news.appuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.AppJwtUtil;
import com.cl.common.util.WmThreadLocal;
import com.news.appuser.dto.ApRegisterDto;
import com.news.appuser.dto.LoginDto;
import com.news.appuser.entity.ApUser;
import com.news.appuser.mapper.ApUserMapper;
import com.news.appuser.service.IApUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    @Override
    public ResponseResult register(ApRegisterDto dto) {
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
    public ResponseResult editor(ApRegisterDto dto) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaQueryWrapper<ApUser> query=new LambdaQueryWrapper<>();
        query.eq(ApUser::getName,dto.getName());
        ApUser one = this.getOne(query);
        if (one == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
        }
        String salt = one.getSalt();
        String pwd = dto.getPassword() + salt;
        String md5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
        LambdaUpdateWrapper<ApUser> update = new LambdaUpdateWrapper<>();
        update.eq(ApUser::getName,dto.getName());
        update.set(ApUser::getPassword,md5);
        update.set(ApUser::getPhone, dto.getPhone());
        update.set(ApUser::getSex,dto.getSex());
        boolean update1 = this.update(update);
        if (update1==true){
            return ResponseResult.okResult();
        }return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }

    @Override
    public ResponseResult get(ApRegisterDto dto) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        if(StringUtils.isEmpty(dto.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_CAN_NOT_EMPTY);
        }
        LambdaQueryWrapper<ApUser> query2=new LambdaQueryWrapper<>();
        query2.eq(ApUser::getName,dto.getName());
        ApUser apUser = this.getOne(query2);
        String password = dto.getPassword();
        String salt = apUser.getSalt();
        password=password+salt;
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        if (s.equals(apUser.getPassword())){
            System.out.println(s.equals(apUser.getPassword()));
            return ResponseResult.okResult("TRUE");
        }
        return ResponseResult.okResult("false");
    }

    @Override
    public ResponseResult login(LoginDto dto) {
        if (!StringUtils.isEmpty(dto.getName()) && !StringUtils.isEmpty(dto.getPassword())) {
            LambdaQueryWrapper<ApUser> query = new LambdaQueryWrapper<>();
            query.eq(ApUser::getName, dto.getName());
            ApUser apUser = this.getOne(query);
            if (apUser == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
            }
            Integer status = apUser.getStatus();
            if (status==0){
                return ResponseResult.errorResult(AppHttpCodeEnum.USER_STATUS);
            }
            String salt = apUser.getSalt();
            String pwd = dto.getPassword() + salt;
            String md5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
            if (!md5.equals(apUser.getPassword())) {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
            String token = AppJwtUtil.getToken(apUser.getId().longValue());
            Map<String, Object> map = new HashMap<>();
            apUser.setPassword("****");
            apUser.setSalt("****");
            apUser.setPhone("****");
            map.put("user", apUser);
            map.put("token", token);
            return ResponseResult.okResult(map);
        }
        if (!StringUtils.isEmpty((dto.getEquipmentId()))) {
            String token = AppJwtUtil.getToken(0L);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            return ResponseResult.okResult(map);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

}