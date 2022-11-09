package com.news.article.service;

import com.cl.common.dto.ResponseResult;
import com.news.article.dto.*;
import com.news.article.entity.ApArticle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文章信息表，存储已发布的文章 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
public interface IApArticleService extends IService<ApArticle> {


    ResponseResult<Long> saveArticle(ArticleDto dto);

    ResponseResult loadArticle(ArticleHomeDto dto, int i);

    ResponseResult loadArticleInfo(Long id);

    ResponseResult deletenews(Long id);

    ResponseResult listByCondition(NewDto dto);

    ResponseResult loadlikelist(LikeNewDto dto);

    ResponseResult loadfocuslist(FocusNewDto dto);

    ResponseResult listByName(NameDto dto);

    ResponseResult loadhistory(HistoryDto dto);
}
