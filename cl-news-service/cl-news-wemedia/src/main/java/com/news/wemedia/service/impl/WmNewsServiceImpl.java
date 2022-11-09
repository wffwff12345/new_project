package com.news.wemedia.service.impl;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.wemedia.dto.ContentDto;
import com.news.wemedia.dto.WmNewsDto;
import com.news.wemedia.dto.WmNewsPageDto;
import com.news.wemedia.entity.WmMaterial;
import com.news.wemedia.entity.WmNews;
import com.news.wemedia.entity.WmNewsMaterial;
import com.news.wemedia.mapper.WmNewsMapper;
import com.news.wemedia.service.IWmMaterialService;
import com.news.wemedia.service.IWmNewsMaterialService;
import com.news.wemedia.service.IWmNewsService;
import com.news.wemedia.task.Tasks;
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
    private IWmMaterialService materialService;
    @Autowired
    @Lazy
    private Tasks tasks;
    @Autowired
    private IWmNewsMaterialService newsMaterialService;
    private String getImagefromList(List<String> images){
        String result=null;
        if(images.size()>0){
            result=String.join(",",images);
        }
        return result;
    }
    private void savaImageRelation(List<String> images, Integer i, Integer id) {
        if (images.size() > 0) {
            int ord = 0;
            for (String image : images) {
                WmNewsMaterial wmNewsMaterial = new WmNewsMaterial();
                LambdaQueryWrapper<WmMaterial> query = new LambdaQueryWrapper<>();
                query.eq(WmMaterial::getUrl, image);
                WmMaterial material = materialService.getOne(query);
                if (material != null) {
                    wmNewsMaterial.setMaterialId(material.getId());
                }
                wmNewsMaterial.setNewsId(id);
                wmNewsMaterial.setType(i);
                wmNewsMaterial.setOrd(ord);
                ord++;
                newsMaterialService.save(wmNewsMaterial);
            }


        }
    }
    @Override
        public ResponseResult listByCondition(WmNewsPageDto dto) {

            IPage<WmNews> page = new Page<>(dto.getPage(), dto.getSize());
            LambdaQueryWrapper<WmNews> query = new LambdaQueryWrapper<>();
            if (dto.getStatus() != null) {
                query.eq(WmNews::getStatus, dto.getStatus());
            }
            if (!StringUtils.isEmpty(dto.getKeyword())) {
                query.like(WmNews::getTitle, dto.getKeyword());
            }
            if (dto.getChannelId() != null) {
                query.eq(WmNews::getChannelId, dto.getChannelId());
            }
            User user = WmThreadLocal.get();
            if (user == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
            query.eq(WmNews::getUserId, user.getUserId());
            query.orderByDesc(WmNews::getPublishTime);
            IPage<WmNews> iPage = this.page(page, query);
            PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(),
                    iPage.getTotal(), iPage.getRecords());
            return result;
        }

    @Override
    public ResponseResult submit(WmNewsDto dto) {
        User user = WmThreadLocal.get();
        if(user==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN); }
        WmNews wmNews = new WmNews();
        if (dto.getId() != null) {
            LambdaQueryWrapper<WmNewsMaterial> query = new LambdaQueryWrapper<>();
            query.eq(WmNewsMaterial::getNewsId, dto.getId());
            newsMaterialService.remove(query);
            wmNews.setId(dto.getId()); }
        wmNews.setUserId(user.getUserId());
        wmNews.setTitle(dto.getTitle());
        wmNews.setContent(dto.getContent()) ;
        wmNews.setType(dto.getType());
        wmNews.setChannelId(dto.getChannelId());
        wmNews.setLabels(dto.getLabels());
        wmNews.setCreatedTime(new Date());
        wmNews.setStatus(1);
        wmNews.setPublishTime(dto.getPublishTime());
        String imagefromList = getImagefromList(dto.getImages());
        wmNews.setImages(imagefromList);
        this.saveOrUpdate(wmNews);
        savaImageRelation(dto.getImages(),1,wmNews.getId());
        List<ContentDto> contentDtos = JSON.parseArray(dto.getContent(), ContentDto.class);
        List<String> contentImages = new ArrayList<>();
        for (ContentDto contentDto : contentDtos) {
            if(contentDto.getType().equals("image")){
                String image = contentDto.getValue();contentImages.add(image); } }
        savaImageRelation(contentImages, 0, wmNews.getId());
        tasks.auditNews(wmNews.getId());
        return ResponseResult.okResult(); }

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
        if (!wmNews.getUserId().equals(user.getUserId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        if (wmNews.getStatus() == 5 ) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_CAN_NOT_DELETE);
        }
        this.removeById(id);
        LambdaQueryWrapper<WmNewsMaterial> query = new LambdaQueryWrapper<>();
        query.eq(WmNewsMaterial::getNewsId, id);
        newsMaterialService.remove(query);
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
        query.eq(WmNews::getUserId,user.getUserId());
        WmNews one = this.getOne(query);
        if (one!=null){
            return ResponseResult.okResult(one);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
}