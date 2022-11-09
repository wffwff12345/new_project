package com.news.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cl.common.dto.ResponseResult;
import com.news.article.entity.ApUserArticlelikes;

/**
 * <p>
 * 管理文章信息表 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-28
 */
public interface IApUserArticlelikesService extends IService<ApUserArticlelikes> {
    ResponseResult updateStatus(Long id, int i);

    ResponseResult loadappuser(Long id);

    ResponseResult addappuser(Long id);

    ResponseResult number();
}
