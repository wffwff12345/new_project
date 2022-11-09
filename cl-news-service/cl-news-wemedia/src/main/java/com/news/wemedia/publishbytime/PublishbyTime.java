package com.news.wemedia.publishbytime;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.news.wemedia.entity.WmNews;
import com.news.wemedia.service.IAuditService;
import com.news.wemedia.service.IWmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublishbyTime {

    @Autowired
    private IWmNewsService newsService;

    @Autowired
    private IAuditService auditService;

    @Scheduled(cron = "0 0/69 * * *  ?")
    public void publishArticle() {
        LambdaQueryWrapper<WmNews> query = new LambdaQueryWrapper<>();
        query.eq(WmNews::getStatus, 4);
        query.select(WmNews::getId);
        List<Integer> ids = newsService.listObjs(query, x ->  (Integer) x);
        for (Integer id : ids) {
            auditService.audit(id);
        }
    }
}
