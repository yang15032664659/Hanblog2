package com.han.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.han.entity.Link;
import org.springframework.stereotype.Repository;

@Repository//代表持久层
public interface LinkMappper extends BaseMapper<Link> {
}
