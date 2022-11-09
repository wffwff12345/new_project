package com.news.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.admin.entity.ApArticleContent;
import com.news.admin.mapper.ApArticleContentMapper;
import com.news.admin.service.IApArticleContentService;
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
