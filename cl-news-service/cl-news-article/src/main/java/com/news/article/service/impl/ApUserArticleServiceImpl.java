package com.news.article.service.impl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cl.common.dto.ResponseResult;
import com.cl.common.dto.User;
import com.cl.common.enums.AppHttpCodeEnum;
import com.cl.common.util.WmThreadLocal;
import com.news.article.dto.CommentsDto;
import com.news.article.entity.ApUserArticle;
import com.news.article.mapper.ApUserArticleMapper;
import com.news.article.service.IApUserArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理文章信息表 服务实现类
 * </p>
 *
 * @author mcm
 * @since 2022-03-28
 */
@Service
public class ApUserArticleServiceImpl extends ServiceImpl<ApUserArticleMapper, ApUserArticle> implements IApUserArticleService {
    @Override
    public ResponseResult comments(CommentsDto dto) {
        User user = WmThreadLocal.get();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        ApUserArticle apUserArticle = new ApUserArticle();
        apUserArticle.setApId(user.getUserId());
        apUserArticle.setArticleId(dto.getArticleId());
        apUserArticle.setComments(dto.getComments());
        this.save(apUserArticle);
        return ResponseResult.okResult();
    }

}