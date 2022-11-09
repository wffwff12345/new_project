package com.news.wemedia.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cl.common.aliyun.OSSService;
import com.cl.common.dto.PageResponseResult;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.WmThreadLocal;
import com.news.wemedia.dto.WmMaterialDto;
import com.news.wemedia.entity.WmMaterial;
import com.news.wemedia.entity.WmNewsMaterial;
import com.news.wemedia.mapper.WmMaterialMapper;
import com.news.wemedia.service.IWmMaterialService;
import com.news.wemedia.service.IWmNewsMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * <p>
 * 自媒体图文素材信息表 服务实现类
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
@Service
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements IWmMaterialService {
    @Autowired
    private OSSService ossService;
    @Autowired
    private IWmNewsMaterialService newsMaterialService;

    @Override
    public ResponseResult upload(MultipartFile file) {
        System.out.println(file);
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
            try {
                String url = ossService.upload(file);
                WmMaterial wmMaterial = new WmMaterial();
                wmMaterial.setUserId(user.getUserId());
                wmMaterial.setUrl(url);
                wmMaterial.setIsCollection(false);
                wmMaterial.setCreatedTime(new Date());
                this.save(wmMaterial);
                return ResponseResult.okResult(wmMaterial);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
            }
        }

    @Override
    public ResponseResult listByCollection(WmMaterialDto dto) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        IPage<WmMaterial> page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmMaterial> query = new LambdaQueryWrapper<>();
        query.eq(WmMaterial::getUserId, user.getUserId());
        if (dto.getIsCollection() != null) {
            query.eq(WmMaterial::getIsCollection, dto.getIsCollection());
        }
        query.orderByDesc(WmMaterial::getCreatedTime);
        IPage<WmMaterial> page1 = this.page(page, query);
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), page1.getTotal(), page1.getRecords());
        return result;
    }

    @Override
    public ResponseResult deleteById(Integer id) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        LambdaUpdateWrapper<WmMaterial> query=new LambdaUpdateWrapper<>();
        query.eq(WmMaterial::getUserId,user.getUserId());
        query.eq(WmMaterial::getId,id);
        WmMaterial wmMaterial = this.getOne(query);
        if (wmMaterial == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        LambdaQueryWrapper<WmNewsMaterial> query1 = new LambdaQueryWrapper<>();
        query1.eq(WmNewsMaterial::getMaterialId, id);
        int count = newsMaterialService.count(query1);
        if (count > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_CAN_NOT_DELETE);
        }
        this.removeById(id);
        ossService.deleteFile(wmMaterial.getUrl());
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateStatus(Integer id, int collection) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaUpdateWrapper<WmMaterial> update = new LambdaUpdateWrapper<>();
        update.eq(WmMaterial::getId, id);
        update.eq(WmMaterial::getUserId,user.getUserId());
        update.set(WmMaterial::getIsCollection, collection);

        boolean update1 = this.update(update);
        if (update1==true){
        return ResponseResult.okResult();
        }return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
}}