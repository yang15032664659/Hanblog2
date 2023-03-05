package com.han.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.constants.SystemConstants;
import com.han.entity.Role;
import com.han.entity.UserRole;
import com.han.mapper.RoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {

        //检查该用户role  id
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getUserId,id);
        UserRole userRole = userRoleService.getOne(lambdaQueryWrapper);
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(userRole.getRoleId() == 1l){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
//        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Role::getId,userRole.getRoleId());
//        wrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
//        wrapper.eq(Role::getDelFlag,SystemConstants.STATUS_NORMAL);
//        List<Role> roles = list(wrapper);
//        List<String> roleKeyList = roles.stream()
//                .map(Role::getRoleKey)
//                .collect(Collectors.toList());
//        return roleKeyList;
        //否则查询用户所具有的角色信息
        return roleMapper.selectRoleKeyByUserId(id);
    }
}
