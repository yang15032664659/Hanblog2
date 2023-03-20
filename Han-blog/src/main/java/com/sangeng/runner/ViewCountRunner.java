package com.han.runner;

import com.han.entity.Article;
import com.han.mapper.ArticleMapper;
import com.han.utils.RedisCache;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
//    @Autowired
//    private ArticleMapper articleMapper;
//    @Autowired
//    private RedisCache redisCache;
//
//    @Override
//    public void run(String... args) throws Exception {
//        //查询博客信息 id viewCount
//        List<Article> articles = articleMapper.selectList(null);
//        Map<String, Integer> viewCountMap = articles.stream()
//                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
//
//
//        redisCache.setCacheMap("article:viewCount",viewCountMap);
//
//    }
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();//
                }));
        //存储到redis中
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
