package com.han.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.entity.Tag;
import com.han.mapper.TagMapper;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper,Tag> implements TagService{
}
