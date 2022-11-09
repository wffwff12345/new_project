package com.news.article.service.impl;

import com.news.article.entity.ApArticleContent;
import com.news.article.mapper.ApArticleContentMapper;
import com.news.article.service.IApArticleContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP已发布文章内容表 服务实现类
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
@Service
public class ApArticleContentServiceImpl extends ServiceImpl<ApArticleContentMapper, ApArticleContent> implements IApArticleContentService {

}
