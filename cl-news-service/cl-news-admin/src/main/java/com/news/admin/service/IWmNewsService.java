package com.news.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cl.common.dto.ResponseResult;
import com.news.admin.dto.NewDto;
import com.news.admin.entity.WmNews;


/**
 * <p>
 * 自媒体图文内容信息表 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
public interface IWmNewsService extends IService<WmNews> {

    ResponseResult listByCondition(NewDto dto);

    ResponseResult deleteById(Integer id);

    ResponseResult getOneById(Integer id);

}
