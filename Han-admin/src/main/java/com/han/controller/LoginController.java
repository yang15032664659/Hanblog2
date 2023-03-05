package com.han.controller;

import com.han.domain.ResponseResult;
import com.han.entity.LoginUser;
import com.han.entity.User;
import com.han.enums.AppHttpCodeEnum;
import com.han.exception.SystemException;
import com.han.service.LoginService;
import com.han.service.UserService;
import com.han.utils.SecurityUtils;
import com.han.vo.AdminUserInfoVo;
import com.han.vo.RoutersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            //提示  必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){

        return loginService.getInfo();
    }

//    @GetMapping("getRouters")
//    public ResponseResult<RoutersVo> getRouters(){
//
//        return loginService.getInfo();
//    }

}
