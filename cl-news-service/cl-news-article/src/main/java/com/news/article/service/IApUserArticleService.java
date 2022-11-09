package com.news.article.service;

import com.cl.common.dto.ResponseResult;
import com.news.article.dto.CommentsDto;
import com.news.article.entity.ApUserArticle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理文章信息表 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-28
 */
public interface IApUserArticleService extends IService<ApUserArticle> {


    ResponseResult comments(CommentsDto dto);

}
