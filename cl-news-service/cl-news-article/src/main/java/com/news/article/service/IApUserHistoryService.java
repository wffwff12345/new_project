package com.news.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cl.common.dto.ResponseResult;
import com.news.article.dto.HistoryDto;
import com.news.article.entity.ApUserHistory;

/**
 * <p>
 * 管理文章信息表 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-28
 */
public interface IApUserHistoryService extends IService<ApUserHistory> {

    ResponseResult addhistory(Long id);
    ResponseResult deletehistory(Long id);
}
