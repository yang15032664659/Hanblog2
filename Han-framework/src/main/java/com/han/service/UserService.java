package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.domain.ResponseResult;
import com.han.entity.User;
import com.han.vo.UserVo;

public interface UserService extends IService<User> {
    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult allUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(UserVo userVo);

    ResponseResult deleteUser(Long id);

    ResponseResult selectUserById(Long id);

    ResponseResult updateUser(UserVo user);
}
