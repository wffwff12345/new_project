package com.news.appuser.service;

import com.cl.common.dto.ResponseResult;
import com.news.appuser.dto.ApRegisterDto;
import com.news.appuser.dto.LoginDto;
import com.news.appuser.entity.ApUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * APP用户信息表 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
public interface IApUserService extends IService<ApUser> {

    ResponseResult login(LoginDto dto);

    ResponseResult register(ApRegisterDto dto);

    ResponseResult editor(ApRegisterDto dto);

    ResponseResult get(ApRegisterDto dto);
}
