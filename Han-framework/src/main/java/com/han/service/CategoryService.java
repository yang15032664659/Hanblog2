package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.domain.ResponseResult;
import com.han.entity.Article;
import com.han.entity.Category;

public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
