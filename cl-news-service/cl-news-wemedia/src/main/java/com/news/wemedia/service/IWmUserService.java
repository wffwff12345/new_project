package com.news.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cl.common.dto.ResponseResult;
import com.news.wemedia.dto.WmLoginDto;
import com.news.wemedia.dto.WmRegisterOrEditorDto;
import com.news.wemedia.entity.WmUser;

/**
 * <p>
 * 自媒体用户信息表 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
public interface IWmUserService extends IService<WmUser> {

    ResponseResult login(WmLoginDto dto);

    ResponseResult register(WmRegisterOrEditorDto dto);

    ResponseResult editor(WmRegisterOrEditorDto dto);

    ResponseResult get(WmRegisterOrEditorDto dto);
}
