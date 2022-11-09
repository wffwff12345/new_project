package com.news.article.dto;

import com.news.article.entity.ApArticle;
import lombok.Data;

@Data
public class ArticleDto extends ApArticle {
    /**
     * 文章内容
     */
    private String content;
}