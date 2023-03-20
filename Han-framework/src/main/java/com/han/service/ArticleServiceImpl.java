package com.han.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.constants.SystemConstants;
import com.han.domain.ResponseResult;
import com.han.domain.dto.AddArticleDto;
import com.han.entity.Article;
import com.han.entity.ArticleTag;
import com.han.entity.Category;
import com.han.mapper.ArticleMapper;

import com.han.utils.BeanCopyUtils;
import com.han.utils.RedisCache;
import com.han.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    @Lazy
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;
    
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> limitPage = new Page<>(SystemConstants.HOT_ARTICLE_PAGE,SystemConstants.HOT_ARTICLE_SIZE);
        page(limitPage,queryWrapper);
        List<Article> articles = limitPage.getRecords();
        //bean拷贝
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        // 如果 有categoryId 就要 查询时要和传入的相同
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        queryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        //查询categoryName
        List<Article> articles = page.getRecords();
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
//        for (Article article : articles) {
//            Category byId = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(byId.getName());
//        }

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis张获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应的id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto,Article.class);
        article.setViewCount(0l);
        article.setDelFlag(0);
        save(article);

        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加博客和标签的关联
        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult allArticle(Integer pageNum, Integer pageSize,String title,String summary) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus,SystemConstants.STATUS_NORMAL);
        wrapper.eq(Article::getDelFlag,SystemConstants.STATUS_NORMAL);
        wrapper.like(StringUtils.hasText(title),Article::getTitle,title);
        wrapper.like(StringUtils.hasText(summary),Article::getSummary,summary);

        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,wrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult selectByArticleId(Long id) {
        Article article = getById(id);
        LambdaQueryWrapper<ArticleTag>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> list = articleTagService.list(wrapper);
        List<Long> tagIds = list.stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        ArticleByIdVo articleByIdVo = BeanCopyUtils.copyBean(article, ArticleByIdVo.class);
        articleByIdVo.setTags(tagIds);
        return ResponseResult.okResult(articleByIdVo);
    }

    @Override
    public ResponseResult updateArticle(ArticleByIdVo articleByIdVo) {
        Article article = BeanCopyUtils.copyBean(articleByIdVo, Article.class);
        updateById(article);
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,article.getId());

        if(articleByIdVo.getTags() != null){
            articleTagService.remove(wrapper);
            List<ArticleTag> articleTags = articleByIdVo.getTags().stream()
                    .map(tagId -> new ArticleTag(article.getId(), tagId))
                    .collect(Collectors.toList());
            articleTagService.saveBatch(articleTags);
        }

        return ResponseResult.okResult(articleByIdVo);
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        Article article = getById(id);
        article.setDelFlag(1);
        updateById(article);

        return ResponseResult.okResult();
    }


}
