package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.domain.ResponseResult;
import com.han.entity.User;
import org.springframework.stereotype.Service;


public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
