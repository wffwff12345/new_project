package com.news.article.service.impl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cl.common.dto.PageResponseResult;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.WmThreadLocal;
import com.news.article.dto.*;
import com.news.article.entity.*;
import com.news.article.mapper.ApArticleMapper;
import com.news.article.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.*;

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
    @Autowired
    private IApUserArticlelikesService apUserArticlelikesService;
    @Autowired
    private IApUserArticlefocusService apUserArticlefocusService;
    @Autowired
    private IApUserHistoryService apUserHistoryService;
    @Override
    public ResponseResult<Long> saveArticle(ArticleDto dto) {
        if (dto.getId() == null) {
            ApArticle article = new ApArticle();

            BeanUtils.copyProperties(dto, article);
            this.save(article);
            ApArticleContent content = new ApArticleContent();
            content.setArticleId(article.getId());
            content.setContent(dto.getContent());
            contentService.save(content);
            return ResponseResult.okResult(article.getId());
        }
        ApArticle article = new ApArticle();
        BeanUtils.copyProperties(dto, article);
        this.updateById(article);
        LambdaUpdateWrapper<ApArticleContent> update = new LambdaUpdateWrapper<>();
        update.eq(ApArticleContent::getArticleId, dto.getId());
        update.set(ApArticleContent::getContent, dto.getContent());
        contentService.update(update);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult loadArticle(ArticleHomeDto dto, int type) {
        if (dto.getSize() == null || dto.getSize() <= 0 || dto.getSize() >100) {
            dto.setSize(10);
        }
        IPage<ApArticle> page=new Page<>(1, dto.getSize());
        LambdaQueryWrapper<ApArticle> query=new LambdaQueryWrapper<>();
        if(dto.getChannelId()!=null&&dto.getChannelId()!=0){
            query.eq(ApArticle::getChannelId,dto.getChannelId());
        }
        query.orderByDesc(ApArticle::getPublishTime);

        //type=1表示获取更多文章
        if(type==1){
            if(dto.getMinTime()==null){
                dto.setMinTime(new Date());
            }
            query.lt(ApArticle::getPublishTime,dto.getMinTime());
        }
        //type=0，表示获取最新文章
        if (type==0){
            if(dto.getMaxTime()==null){
                dto.setMaxTime(new Date());
            }
            query.gt(ApArticle::getPublishTime,dto.getMaxTime());
        }
        IPage<ApArticle> page1 = this.page(page, query);
        return ResponseResult.okResult(page1.getRecords()) ;
    }

    @Override
    public ResponseResult loadArticleInfo(Long id) {
        ApArticle article = this.getById(id);
        if(article==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        LambdaQueryWrapper<ApArticleContent> query=new LambdaQueryWrapper<>();
        query.eq(ApArticleContent::getArticleId,id);
        ApArticleContent apArticleContent = contentService.getOne(query);
        Map<String, Object> map = new HashMap<>();
        map.put("config",article);
        map.put("content",apArticleContent);
        return ResponseResult.okResult(map);
    }

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

    @Override
    public ResponseResult listByCondition(NewDto dto) {
        if (StringUtils.isEmpty(dto.getTitle())){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_CAN_NOT_EMPTY);
        }
        IPage<ApArticle> page1 = new Page<>(1,dto.getSize());
        LambdaQueryWrapper<ApArticle> query=new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(dto.getTitle())){
         query.like(ApArticle::getTitle,dto.getTitle());
        }
        IPage<ApArticle> page = this.page(page1,query);
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(),page.getTotal(),page.getRecords());
        return result;
    }

    @Override
    public ResponseResult loadlikelist(LikeNewDto dto) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaQueryWrapper<ApUserArticlelikes> query=new LambdaQueryWrapper<>();
        query.eq(ApUserArticlelikes::getApId,user.getUserId());
        query.eq(ApUserArticlelikes::getIsLike,1);
        query.select(ApUserArticlelikes::getArticleId);
        List<BigInteger> ids = apUserArticlelikesService.listObjs(query, x ->  (BigInteger) x);
        if (ids.size()==0){
            return ResponseResult.okResult(ids);
        }
        LambdaQueryWrapper<ApArticle> query2=new LambdaQueryWrapper<>();
        IPage<ApArticle> page=new Page<>(1,dto.getSize());
        query2.in(ApArticle::getId,ids);
        query2.orderByDesc(ApArticle::getPublishTime);
        IPage<ApArticle> page1 = this.page(page, query2);
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(),page1.getTotal(),page1.getRecords());
        return result;
    }

    @Override
    public ResponseResult loadfocuslist(FocusNewDto dto) {
        User user = WmThreadLocal.get();
        if (user==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaQueryWrapper<ApUserArticlefocus> query=new LambdaQueryWrapper<>();
        query.eq(ApUserArticlefocus::getApId,user.getUserId());
        query.eq(ApUserArticlefocus::getIsFocused,1);
        IPage<ApUserArticlefocus> page = new Page<>(1,dto.getSize());
        IPage<ApUserArticlefocus> page1 = apUserArticlefocusService.page(page, query);
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(),page1.getTotal(),page1.getRecords());
        return result;
    }

    @Override
    public ResponseResult listByName(NameDto dto) {
        User user = WmThreadLocal.get();
        if (user==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        IPage<ApArticle> page1 = new Page<>(1,dto.getSize());
        LambdaQueryWrapper<ApArticle> query=new LambdaQueryWrapper<>();
        query.eq(ApArticle::getAuthorName,dto.getName());
        query.orderByDesc(ApArticle::getPublishTime);
        IPage<ApArticle> page = this.page(page1,query);
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(),page.getTotal(),page.getRecords());
        return result;
    }

    @Override
    public ResponseResult loadhistory(HistoryDto dto) {
        User user = WmThreadLocal.get();
        if (user==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        /*LambdaQueryWrapper<ApUserHistory> query4=new LambdaQueryWrapper<>();
        query4.eq(ApUserHistory::getApId,user.getUserId());
        query4.select(ApUserHistory::getArticleId);
        query4.orderByDesc(ApUserHistory::getLookTime);
        List<Object> objects = apUserHistoryService.listObjs(query4, x -> (BigInteger) x);*/
        IPage<ApUserHistory> page1=new Page<>(1,dto.getSize());
        LambdaQueryWrapper<ApUserHistory> query=new LambdaQueryWrapper<>();
        query.eq(ApUserHistory::getApId,user.getUserId());
        query.orderByDesc(ApUserHistory::getLookTime);
        IPage<ApUserHistory> page2 = apUserHistoryService.page(page1,query);
        /*if (bigIntegers.size()==0){
            return ResponseResult.okResult(bigIntegers);
        }*/

        /*LambdaQueryWrapper<ApArticle> query2=new LambdaQueryWrapper<>();
        IPage<ApArticle> page1=new Page<>(1,dto.getSize());
        query2.in(ApArticle::getId,bigIntegers);
        query2.orderByDesc(ApArticle::getPublishTime);
        IPage<ApArticle> page2 = this.page(page1,query2);*/
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(),page2.getTotal(),page2.getRecords());
        return result;
    }


}
