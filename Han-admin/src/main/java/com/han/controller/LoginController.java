package com.han.controller;

import com.han.domain.ResponseResult;
import com.han.entity.LoginUser;
import com.han.entity.Menu;
import com.han.entity.User;
import com.han.enums.AppHttpCodeEnum;
import com.han.exception.SystemException;
import com.han.service.LoginService;
import com.han.service.MenuService;
import com.han.service.RoleService;
import com.han.service.UserService;
import com.han.utils.BeanCopyUtils;
import com.han.utils.SecurityUtils;
import com.han.vo.AdminUserInfoVo;
import com.han.vo.RoutersVo;
import com.han.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){

        return loginService.getRouters();
    }
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }


}
//@RestController
//public class LoginController {
//    @Autowired
//    private LoginService loginService;
//
//    @Autowired
//    private MenuService menuService;
//
//    @Autowired
//    private RoleService roleService;
//
//    @PostMapping("/user/login")
//    public ResponseResult login(@RequestBody User user){
//        if(!StringUtils.hasText(user.getUserName())){
//            //提示 必须要传用户名
//            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
//        }
//        return loginService.login(user);
//    }
//
//    @GetMapping("getInfo")
//    public ResponseResult<AdminUserInfoVo> getInfo(){
//        //获取当前登录的用户
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//        //根据用户id查询权限信息
//        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
//        //根据用户id查询角色信息
//        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
//
//        //获取用户信息
//        User user = loginUser.getUser();
//        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
//        //封装数据返回
//
//        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
//        return ResponseResult.okResult(adminUserInfoVo);
//    }
//
//    @GetMapping("getRouters")
//    public ResponseResult<RoutersVo> getRouters(){
//        Long userId = SecurityUtils.getUserId();
//        //查询menu 结果是tree的形式
//        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
//        //封装数据返回
//        return ResponseResult.okResult(new RoutersVo(menus));
//    }
//
//    @PostMapping("/user/logout")
//    public ResponseResult logout(){
//
//        return loginService.logout();
//    }
//
//}
