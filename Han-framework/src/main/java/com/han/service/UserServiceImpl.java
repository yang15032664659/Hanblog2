package com.han.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.entity.Comment;
import com.han.entity.User;
import com.han.mapper.CommentMapper;
import com.han.mapper.UserMappper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMappper, User> implements UserService {
}
