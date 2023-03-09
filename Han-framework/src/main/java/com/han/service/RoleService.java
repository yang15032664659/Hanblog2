package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.domain.ResponseResult;
import com.han.domain.dto.RoleDto;
import com.han.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult allRole(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(RoleDto roleDto);

    ResponseResult addRole(Role role);

    ResponseResult selectRoleById(Long id);


    ResponseResult updateRole(Role role);

    ResponseResult deleteRole(Long id);

    ResponseResult listAllRole();
}
