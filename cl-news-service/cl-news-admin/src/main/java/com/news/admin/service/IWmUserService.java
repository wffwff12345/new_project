package com.news.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cl.common.dto.ResponseResult;
import com.news.admin.dto.ManageUserDto;
import com.news.admin.dto.WmRegisterOrEditorDto;
import com.news.admin.entity.WmUser;

/**
 * <p>
 * 自媒体用户信息表 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
public interface IWmUserService extends IService<WmUser> {


    ResponseResult deleteuser(Integer id);

    ResponseResult ListByName(ManageUserDto dto);

    ResponseResult AddUser(WmRegisterOrEditorDto dto);

    ResponseResult upadtestatus(Integer id, Integer type);
}
