package com.han.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.entity.UserRole;
import com.han.mapper.UserRoleMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
