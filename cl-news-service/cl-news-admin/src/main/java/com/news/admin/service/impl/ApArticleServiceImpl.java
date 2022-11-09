package com.news.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cl.common.dto.PageResponseResult;
import com.cl.common.dto.ResponseResult;
import com.cl.common.enums.AppHttpCodeEnum;
import com.news.admin.entity.ApArticle;
import com.news.admin.entity.ApArticleContent;
import com.news.admin.mapper.ApArticleMapper;
import com.news.admin.service.IApArticleContentService;
import com.news.admin.service.IApArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 文章信息表，存储已发布的文章 服务实现类
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
@Service
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements IApArticleService {
    @Autowired
    private IApArticleContentService contentService;


    @Override
    public ResponseResult deletenews(Long id) {
        ApArticle apArticle = this.getById(id);
        if (apArticle == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        this.removeById(id);
        LambdaUpdateWrapper<ApArticleContent> query=new LambdaUpdateWrapper<>();
        query.eq(ApArticleContent::getArticleId,id);
        contentService.remove(query);
        return ResponseResult.okResult();
    }



}
