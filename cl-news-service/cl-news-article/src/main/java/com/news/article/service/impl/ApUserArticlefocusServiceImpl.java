package com.news.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.WmThreadLocal;
import com.news.article.entity.ApArticle;
import com.news.article.entity.ApUserArticlefocus;
import com.news.article.entity.ApUserArticlelikes;
import com.news.article.mapper.ApUserArticlefocusMapper;
import com.news.article.service.IApArticleService;
import com.news.article.service.IApUserArticlefocusService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ApUserArticlefocusServiceImpl extends ServiceImpl<ApUserArticlefocusMapper, ApUserArticlefocus> implements IApUserArticlefocusService {
    @Autowired
    private IApArticleService apArticleService;
    @Override
    public ResponseResult updateStatus(Long id, int i) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        ApArticle byId = apArticleService.getById(id);
        String authorName = byId.getAuthorName();
        LambdaUpdateWrapper<ApUserArticlefocus> update = new LambdaUpdateWrapper<>();
        /*update.eq(ApUserArticlefocus::getArticleId,id);*/
        update.eq(ApUserArticlefocus::getApId,user.getUserId());
        update.eq(ApUserArticlefocus::getAuthorName, authorName);
        update.set(ApUserArticlefocus::getIsFocused, i);
        boolean update1 = this.update(update);
        if (update1==true){
            return ResponseResult.okResult();
        }return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }

    @Override
    public ResponseResult loadappuser(Long id) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        LambdaUpdateWrapper<ApUserArticlefocus> query=new LambdaUpdateWrapper<>();
        ApArticle byId = apArticleService.getById(id);
        String authorName = byId.getAuthorName();
        query.eq(ApUserArticlefocus::getAuthorName,authorName);
        query.eq(ApUserArticlefocus::getApId,user.getUserId());
        ApUserArticlefocus result = this.getOne(query);
        if (result!=null){
            return ResponseResult.okResult(result);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }

    @Override
    public ResponseResult addappuser(Long id) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        ApArticle byId = apArticleService.getById(id);
        String authorName = byId.getAuthorName();
        ApUserArticlefocus articlefocus = new ApUserArticlefocus();
        articlefocus.setApId(user.getUserId());
        articlefocus.setAuthorName(authorName);
        articlefocus.setIsFocused(0);
        this.save(articlefocus);
        return ResponseResult.okResult(articlefocus);
    }

    @Override
    public ResponseResult focusnumber() {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaUpdateWrapper<ApUserArticlefocus> query=new LambdaUpdateWrapper<>();
        query.eq(ApUserArticlefocus::getApId,user.getUserId());
        query.eq(ApUserArticlefocus::getIsFocused,1);
        int count = this.count(query);
        return ResponseResult.okResult(count);
    }
}

