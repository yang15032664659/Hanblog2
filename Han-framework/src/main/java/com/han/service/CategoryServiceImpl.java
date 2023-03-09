package com.han.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.constants.SystemConstants;
import com.han.domain.ResponseResult;
import com.han.domain.dto.CategoryDto;
import com.han.entity.Article;
import com.han.entity.Category;
import com.han.mapper.CategoryMapper;
import com.han.utils.BeanCopyUtils;
import com.han.vo.CateGoryVo2;
import com.han.vo.CateGoryVo3;
import com.han.vo.CategoryVo;
import com.han.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    @Lazy
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态为已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表

        List<Category> categories = (List<Category>) listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);




    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus,SystemConstants.STATUS_NORMAL);
        wrapper.eq(Category::getDelFlag,SystemConstants.STATUS_NORMAL);
        List<Category> list = list(wrapper);
        List<CateGoryVo2> cateGoryVo2s = BeanCopyUtils.copyBeanList(list, CateGoryVo2.class);
        return ResponseResult.okResult(cateGoryVo2s);
    }


    @Override
    public ResponseResult allList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status),Category::getStatus,status);
        wrapper.like(StringUtils.hasText(name),Category::getName,name);
        wrapper.eq(Category::getDelFlag,SystemConstants.STATUS_NORMAL);
        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,wrapper);
        List<CateGoryVo3> cateGoryVo3s = BeanCopyUtils.copyBeanList(page.getRecords(), CateGoryVo3.class);
        PageVo pageVo = new PageVo(cateGoryVo3s,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectCategory(Long id) {
        Category category = getById(id);
        CateGoryVo3 cateGoryVo3 = BeanCopyUtils.copyBean(category,CateGoryVo3.class);
        return ResponseResult.okResult(cateGoryVo3);
    }

    @Override
    public ResponseResult updateCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        Category category = getById(id);
        category.setDelFlag(1);
        updateById(category);
        return ResponseResult.okResult();
    }


}
