package com.han.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.entity.ArticleTag;
import com.han.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService{

}
