package com.han.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.han.entity.Article;
import com.han.entity.Category;
import org.springframework.stereotype.Repository;

@Repository//代表持久层
public interface CategoryMapper extends BaseMapper<Category> {
}
