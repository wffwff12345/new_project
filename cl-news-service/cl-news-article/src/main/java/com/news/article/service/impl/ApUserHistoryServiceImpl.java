package com.news.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.WmThreadLocal;
import com.news.article.dto.HistoryDto;
import com.news.article.entity.ApUserArticlelikes;
import com.news.article.entity.ApUserHistory;
import com.news.article.mapper.ApUserHistoryMapper;
import com.news.article.service.IApUserHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 * APP已发布文章内容表 服务实现类
 * </p>
 *
 * @author mcm
 * @since 2022-03-26
 */
@Service
public class ApUserHistoryServiceImpl extends ServiceImpl<ApUserHistoryMapper, ApUserHistory> implements IApUserHistoryService {

    @Override
    public ResponseResult addhistory(Long id) {
        User user = WmThreadLocal.get();
        if (user==null)
        {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaQueryWrapper<ApUserHistory> query=new LambdaQueryWrapper<>();
        query.eq(ApUserHistory::getApId,user.getUserId());
        query.eq(ApUserHistory::getArticleId,id);
        ApUserHistory one = this.getOne(query);
        if (one!=null){
            LambdaUpdateWrapper<ApUserHistory> query2=new LambdaUpdateWrapper<>();
            query2.eq(ApUserHistory::getApId,user.getUserId());
            query2.eq(ApUserHistory::getArticleId,id);
            query2.set(ApUserHistory::getLookTime,new Date());
            this.update(query2);
            return ResponseResult.okResult();

        }
        ApUserHistory apUserHistory = new ApUserHistory();
        apUserHistory.setApId(user.getUserId());
        apUserHistory.setArticleId(id);
        apUserHistory.setLookTime(new Date());
        this.save(apUserHistory);
        return ResponseResult.okResult(apUserHistory);
    }

    @Override
    public ResponseResult deletehistory(Long id) {
        User user = WmThreadLocal.get();
        if (user==null)
        {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        LambdaQueryWrapper<ApUserHistory> query=new LambdaQueryWrapper<>();
        query.eq(ApUserHistory::getApId,user.getUserId());
        query.eq(ApUserHistory::getArticleId,id);
        this.remove(query);
        return ResponseResult.okResult();
    }


}

