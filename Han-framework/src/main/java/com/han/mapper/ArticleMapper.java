package com.han.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.entity.Article;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//在对应的接口上面继承一个基本的接口 BaseMapper
@Repository//代表持久层
public interface ArticleMapper extends BaseMapper<Article> {


}
