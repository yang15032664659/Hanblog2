package com.han.job;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.han.entity.Article;
import com.han.entity.User;
import com.han.mapper.ArticleMapper;
import com.han.service.ArticleService;
import com.han.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;

    @Scheduled(cron = "0/59 * * * * ?")
    @Transactional
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //TODO 更新到数据库中  一直报空指针异常
//        for (Article article : articles) {
//            LambdaUpdateWrapper<Article> lambda = new UpdateWrapper<Article>().lambda();
//            lambda.set(Article::getViewCount, article.getViewCount())
//                    .eq(Article::getId, article.getId());
//            articleMapper.update(null,lambda);
//
////            this.update(lambda);//提交
////            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
////            updateWrapper.eq(Article :: getId, article.getId());
////            updateWrapper.set(Article :: getViewCount, article.getViewCount());
////            articleService.update(updateWrapper);
//        }
        for (Article article : articles) {
//            UpdateWrapper<Article> updateWrapper = new UpdateWrapper();
//            updateWrapper.eq("id",article.getId())
//                            .set("view_count",article.getViewCount());
//            articleService.update(null,updateWrapper);

            UpdateWrapper<Article> updateWrapper = new UpdateWrapper();
            updateWrapper.eq("id",article.getId());
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Article::getId,article.getId());
            Article article1 = articleService.getOne(queryWrapper);
            article1.setViewCount(article.getViewCount());
            System.out.println("article1:" + article1.getId() + "  " + article1.getViewCount());
            articleService.update(article1,updateWrapper);
        }
    }

//    @Autowired
//    private RedisCache redisCache;
//    @Autowired
//    private ArticleService articleService;
//    @Scheduled(cron = "0/5 * * * * ?")
//
//    public void updateViewCount(){
//        //获取redis中的浏览量
//        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
//        List<Article> articles = viewCountMap.entrySet()
//                .stream()
//                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
//                .collect(Collectors.toList());
//        //更新到数据库中
//        for (Article article : articles) {
//            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//            articleService.updateById(article);
////            queryWrapper.eq(User::getId,article.getId())
////                            .set("viewCount",article.getViewCount());
////            articleService.update(null,updateWrapper);
//        }
////        articleService.updateBatchById(articles);
//    }
}
