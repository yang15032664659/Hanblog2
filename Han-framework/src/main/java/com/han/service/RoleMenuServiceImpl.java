package com.han.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.entity.RoleMenu;
import com.han.mapper.RoleMapper;
import com.han.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService{

}
