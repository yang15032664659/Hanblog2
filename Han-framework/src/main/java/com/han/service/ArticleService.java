package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.domain.ResponseResult;
import com.han.domain.dto.AddArticleDto;
import com.han.entity.Article;
import com.han.vo.ArticleByIdVo;


public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);


    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult allArticle(Integer pageNum, Integer pageSize,String title,String summary);

    ResponseResult selectByArticleId(Long id);

    ResponseResult updateArticle(ArticleByIdVo article);

    ResponseResult deleteArticle(Long id);
}
