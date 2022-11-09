package com.news.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cl.common.dto.ResponseResult;
import com.news.admin.dto.ApRegisterDto;
import com.news.admin.dto.ManageUserDto;
import com.news.admin.entity.ApUser;

/**
 * <p>
 * APP用户信息表 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
public interface IApUserService extends IService<ApUser> {


    ResponseResult ListByName(ManageUserDto dto);

    ResponseResult deleteuser(Long id);

    ResponseResult AddUser(ApRegisterDto dto);

    ResponseResult updatestatus(Integer id,Integer type);
}