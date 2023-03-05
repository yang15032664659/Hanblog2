package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);
}
