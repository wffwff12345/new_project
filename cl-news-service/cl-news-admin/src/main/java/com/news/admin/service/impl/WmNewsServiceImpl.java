package com.news.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cl.common.dto.PageResponseResult;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.WmThreadLocal;
import com.news.admin.dto.NewDto;
import com.news.admin.entity.WmNews;
import com.news.admin.entity.WmNewsMaterial;
import com.news.admin.feign.NewsFeign;
import com.news.admin.mapper.WmNewsMapper;
import com.news.admin.service.IApArticleService;
import com.news.admin.service.IWmNewsMaterialService;
import com.news.admin.service.IWmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 自媒体图文内容信息表 服务实现类
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
@Service
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements IWmNewsService {
    @Autowired
    private IApArticleService articleService;
    @Autowired
    private IWmNewsMaterialService newsMaterialService;
    @Override
        public ResponseResult listByCondition(NewDto dto) {

            IPage<WmNews> page = new Page<>(dto.getPage(), dto.getSize());
            LambdaQueryWrapper<WmNews> query = new LambdaQueryWrapper<>();
            if (dto.getTitle() != null) {
                query.like(WmNews::getTitle, dto.getTitle());
            }
            User user = WmThreadLocal.get();
            if (user == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
            query.orderByDesc(WmNews::getPublishTime);
            IPage<WmNews> iPage = this.page(page, query);
            PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(),
                    iPage.getTotal(), iPage.getRecords());
            return result;
        }


    @Override
    public ResponseResult deleteById(Integer id) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        WmNews wmNews = this.getById(id);
        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        LambdaUpdateWrapper<WmNews> query1=new LambdaUpdateWrapper<>();
        LambdaQueryWrapper<WmNewsMaterial> query2 = new LambdaQueryWrapper<>();
        query1.eq(WmNews::getId,id);
        query2.eq(WmNewsMaterial::getNewsId, id);
        WmNews one = this.getOne(query1);
        Long articleId = one.getArticleId();
        this.removeById(id);
        newsMaterialService.remove(query2);
        articleService.deletenews(articleId);
        return ResponseResult.okResult();

}
    @Override
    public ResponseResult getOneById(Integer id) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaUpdateWrapper<WmNews> query=new LambdaUpdateWrapper<>();
        query.eq(WmNews::getId,id);
        WmNews one = this.getOne(query);
        if (one!=null){
            return ResponseResult.okResult(one);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
}