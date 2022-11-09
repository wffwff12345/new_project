package com.news.admin.service;

import com.cl.common.dto.ResponseResult;
import com.news.admin.dto.AdRejisterUser;
import com.news.admin.dto.AdUserDto;
import com.news.admin.entity.AdUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员用户信息表 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-16
 */
public interface IAdUserService extends IService<AdUser> {
    ResponseResult login(AdUserDto dto);

    ResponseResult register(AdRejisterUser dto);

    ResponseResult editor(AdRejisterUser dto);

    ResponseResult get(AdRejisterUser dto);
}
