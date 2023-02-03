package com.han.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.entity.Article;
import com.han.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
