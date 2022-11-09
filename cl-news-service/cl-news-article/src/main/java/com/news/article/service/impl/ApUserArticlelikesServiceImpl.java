package com.news.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.WmThreadLocal;
import com.news.article.entity.ApUserArticlelikes;
import com.news.article.mapper.ApUserArticlelikesMapper;
import com.news.article.service.IApUserArticlelikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * APP已发布文章内容表 服务实现类
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
@Service
public class ApUserArticlelikesServiceImpl extends ServiceImpl<ApUserArticlelikesMapper, ApUserArticlelikes> implements IApUserArticlelikesService {
    @Override
    public ResponseResult updateStatus(Long id, int i) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaUpdateWrapper<ApUserArticlelikes> update = new LambdaUpdateWrapper<>();
        update.eq(ApUserArticlelikes::getApId,user.getUserId());
        update.eq(ApUserArticlelikes::getArticleId, id);
        update.set(ApUserArticlelikes::getIsLike, i);
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

        LambdaUpdateWrapper<ApUserArticlelikes> query=new LambdaUpdateWrapper<>();
        query.eq(ApUserArticlelikes::getArticleId,id);
        query.eq(ApUserArticlelikes::getApId,user.getUserId());
        ApUserArticlelikes result = this.getOne(query);
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
        ApUserArticlelikes articlelikes = new ApUserArticlelikes();
        articlelikes.setApId(user.getUserId());
        articlelikes.setArticleId(id);
        articlelikes.setIsLike(0);
        this.save(articlelikes);
        return ResponseResult.okResult(articlelikes);
    }

    @Override
    public ResponseResult number() {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaUpdateWrapper<ApUserArticlelikes> query=new LambdaUpdateWrapper<>();
        query.eq(ApUserArticlelikes::getApId,user.getUserId());
        query.eq(ApUserArticlelikes::getIsLike,1);
        int count = this.count(query);
        return ResponseResult.okResult(count);
    }
}

