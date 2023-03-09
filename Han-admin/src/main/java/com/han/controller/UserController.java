package com.han.controller;

import com.han.domain.ResponseResult;
import com.han.entity.User;
import com.han.service.UserService;
import com.han.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/list")
    public ResponseResult allUser(Integer pageNum,Integer pageSize,String userName,String phonenumber,String status){
        return userService.allUser(pageNum,pageSize,userName,phonenumber,status);
    }
    @PostMapping
    public ResponseResult addUser(@RequestBody UserVo userVo){
        return userService.addUser(userVo);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
    @GetMapping("/{id}")
    public ResponseResult selectUserById(@PathVariable Long id){
        return userService.selectUserById(id);
    }
    @PutMapping
    public ResponseResult updateUser(@RequestBody UserVo userVo){
        return userService.updateUser(userVo);

    }

}
