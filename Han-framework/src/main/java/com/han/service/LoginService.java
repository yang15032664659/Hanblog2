package com.han.service;

import com.han.domain.ResponseResult;
import com.han.entity.User;
import com.han.vo.AdminUserInfoVo;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult<AdminUserInfoVo> getInfo();
}
