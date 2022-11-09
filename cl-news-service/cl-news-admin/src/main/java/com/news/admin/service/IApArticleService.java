package com.news.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cl.common.dto.ResponseResult;
import com.news.admin.entity.ApArticle;


/**
 * <p>
 * 文章信息表，存储已发布的文章 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
public interface IApArticleService extends IService<ApArticle> {




    ResponseResult deletenews(Long id);


}
