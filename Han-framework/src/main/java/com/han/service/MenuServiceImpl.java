package com.han.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.constants.SystemConstants;
import com.han.entity.Menu;
import com.han.entity.RoleMenu;
import com.han.entity.UserRole;
import com.han.mapper.MenuMapper;
import com.han.mapper.RoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //检查该用户role  id
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getUserId,id);
        UserRole userRole = userRoleService.getOne(lambdaQueryWrapper);
        //如果是管理员，返回所有的权限
        if(userRole.getRoleId() == 1l){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.TYPE_MENU,SystemConstants.TYPE_BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }

        //否则返回所具有的权限
        return menuMapper.selectPermsByUserId(id);
    }

//    @Override
//    public List<String> selectPermsByUserId(Long id) {
//        //检查该用户role  id
//        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(UserRole::getUserId,id);
//        UserRole userRole = userRoleService.getOne(lambdaQueryWrapper);
//        //如果是管理员，返回所有的权限
//        if(userRole.getRoleId() == 1l){
//            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
//            wrapper.in(Menu::getMenuType, SystemConstants.TYPE_MENU,SystemConstants.TYPE_BUTTON);
//            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
//            List<Menu> menus = list(wrapper);
//            List<String> perms = menus.stream()
//                    .map(Menu::getPerms)
//                    .collect(Collectors.toList());
//            return perms;
//        }
//        //否则返回所具有的权限
//        List<Menu> menus2 = new ArrayList<>();
//        LambdaQueryWrapper<RoleMenu> wrapper1 = new LambdaQueryWrapper<>();
//        wrapper1.eq(RoleMenu::getRoleId,userRole.getRoleId());
//        List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper1);
//        for (RoleMenu roleMenu : roleMenus) {
//            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
//
//            wrapper.in(Menu::getMenuType, SystemConstants.TYPE_MENU,SystemConstants.TYPE_BUTTON);
//            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
//            wrapper.eq(Menu::getId,roleMenu.getMenuId());
////            System.out.println(getOne(wrapper));
//            Menu menu = getOne(wrapper);
//            if(menu != null){
//                menus2.add(menu);
//            }
//
//        }
//        List<String> perms2 = menus2.stream()
//                .map(Menu::getPerms)
//                .collect(Collectors.toList());
//        return perms2;
//
//    }
}
