package com.han.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.han.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository//代表持久层
public interface CommentMapper extends BaseMapper<Comment> {
}
