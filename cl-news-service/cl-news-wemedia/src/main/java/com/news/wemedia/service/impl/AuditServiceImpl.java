package com.news.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.cl.common.aliyun.GreenImageScan;
import com.cl.common.aliyun.GreenTextScan;
import com.cl.common.dto.ResponseResult;
import com.cl.common.enums.AppHttpCodeEnum;
import com.news.wemedia.dto.AdChannel;
import com.news.wemedia.dto.ArticleDto;
import com.news.wemedia.dto.ContentDto;
import com.news.wemedia.entity.WmNews;
import com.news.wemedia.entity.WmUser;
import com.news.wemedia.feign.AdminFeign;
import com.news.wemedia.feign.ArticleFeign;
import com.news.wemedia.service.IAuditService;
import com.news.wemedia.service.IWmNewsService;
import com.news.wemedia.service.IWmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AuditServiceImpl implements IAuditService {

    @Autowired
    private IWmNewsService newsService;
    @Autowired
    private GreenTextScan textScan;
    @Autowired
    private GreenImageScan imageScan;
    @Autowired
    private ArticleFeign articleFeign;
    @Autowired
    private IWmUserService userService;
    @Autowired
    private AdminFeign adminFeign;
    private void saveArticle(WmNews wmNews) {
        wmNews.setStatus(5);
        ArticleDto dto = new ArticleDto();
        dto.setTitle(wmNews.getTitle());
        WmUser wmUser = userService.getById(wmNews.getUserId());
        dto.setAuthorName(wmUser.getName());
        dto.setChannelId(wmNews.getChannelId());
        ResponseResult<AdChannel> ResponseResult1 = adminFeign.getChannelById(wmNews.getChannelId());
        if (ResponseResult1.getCode().equals(AppHttpCodeEnum.SUCCESS.getCode())) {
            AdChannel adChannel = ResponseResult1.getData();
            dto.setChannelName(adChannel.getName());
        }

        dto.setLayout(wmNews.getType());
        dto.setImages(wmNews.getImages());
        dto.setLabels(wmNews.getLabels());
        dto.setContent(wmNews.getContent());
        dto.setCreatedTime(wmNews.getCreatedTime());
        dto.setPublishTime(wmNews.getPublishTime());
        ResponseResult<Long> ResponseResult = articleFeign.saveArticle(dto);
        if (ResponseResult.getCode().equals(AppHttpCodeEnum.SUCCESS.getCode())) {
            Long articleId = ResponseResult.getData();
            wmNews.setArticleId(articleId);
            newsService.updateById(wmNews);
        }
    }
    @Override
    public void audit(Integer id) {
        WmNews wmNews = newsService.getById(id);
        if (wmNews == null) {
            return;
        }
        Date now = new Date();
        if (wmNews.getStatus() == 4) {
            if (wmNews.getPublishTime().getTime() <= now.getTime()) {
                saveArticle(wmNews);
            }
            return;
        }

        if (wmNews.getStatus() == 1) {
            List<ContentDto> contentDtos = JSON.parseArray(wmNews.getContent(), ContentDto.class);
            StringBuilder sb = new StringBuilder();
            List<String> contentImages = new ArrayList<>();
            for (ContentDto contentDto : contentDtos) {
                if (contentDto.getType().equals("text")) {
                    sb.append(contentDto.getValue());
                } else {
                    contentImages.add(contentDto.getValue());
                }
            }
            String scanText = wmNews.getTitle() + sb.toString();
            boolean textResult = textAudit(scanText, wmNews);
            if (textResult) {
                String images = wmNews.getImages();
                String[] split = images.split(",");
                for (String s : split) {
                    if (!StringUtils.isEmpty(s)) {
                        if (!contentImages.contains(s)) {
                            contentImages.add(s);
                        }
                    }
                }
                boolean imageResult = imageAudit(contentImages, wmNews);
                if (imageResult) {
                    if (wmNews.getPublishTime().getTime() > now.getTime()) {
                        wmNews.setStatus(4);
                        newsService.updateById(wmNews);
                        return;
                    }
                    saveArticle(wmNews);
                }
            }
        }
    }
    private boolean imageAudit(List<String> contentImages, WmNews wmNews) {
        boolean result = false;
        if (contentImages.size() <= 0) {
            return true;
        }
        try {
            Map map = imageScan.checkUrl(contentImages);
            String suggestion = (String) map.get("suggestion");
            switch (suggestion) {
                case "pass":
                    result = true;
                    break;
                case "review":
                    wmNews.setStatus(3);
                    String label = (String) map.get("label");
                    wmNews.setReason("文本审核需要复审: " + label);
                    newsService.updateById(wmNews);
                    break;
                case "block":
                    wmNews.setStatus(2);
                    label = (String) map.get("label");
                    wmNews.setReason("文本审核失败: " + label);
                    newsService.updateById(wmNews);
                    break; }
        } catch (Exception e) {
            e.printStackTrace(); }
        return result; }
    private boolean textAudit(String content, WmNews wmNews) {
        boolean result = false;
        try {
            Map map = textScan.greenTextScan(content);
            String suggestion = (String) map.get("suggestion");
            switch (suggestion) {
                case "pass":
                    result = true;
                    break;
                case "review":
                    wmNews.setStatus(3);
                    String label = (String) map.get("label");
                    wmNews.setReason("文本审核需要复审: " + label);
                    newsService.updateById(wmNews);
                    break;
                case "block":
                    wmNews.setStatus(2);
                    label = (String) map.get("label");
                    wmNews.setReason("文本审核失败: " + label);
                    newsService.updateById(wmNews);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
