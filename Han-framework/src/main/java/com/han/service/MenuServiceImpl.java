package com.han.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.constants.SystemConstants;
import com.han.domain.ResponseResult;
import com.han.entity.Menu;
import com.han.entity.RoleMenu;
import com.han.entity.UserRole;
import com.han.enums.AppHttpCodeEnum;
import com.han.mapper.MenuMapper;
import com.han.mapper.RoleMenuMapper;
import com.han.vo.MenuTreeVo;
import com.han.vo.RoleVo2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Autowired
    private RoleMenuService roleMenuService;
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



    @Override
    public ResponseResult allMenu(String status, String menuName) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status),Menu::getStatus,status);
        wrapper.like(StringUtils.hasText(menuName), Menu::getMenuName,menuName);
        wrapper.orderByAsc(Menu::getParentId);
        wrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menus = list(wrapper);


        return ResponseResult.okResult(menus);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectMenuById(Long id) {
        Menu menu = getById(id);
        return ResponseResult.okResult(menu);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if(menu.getId().equals(menu.getParentId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.MENU_UPDATE_ERROR);
        }else{
            updateById(menu);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,id);
        List<Menu> list = list(wrapper);
        if(list.size() == 0){
            removeById(id);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.MENU_DELETE_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        List<Menu> menus = list(null);
        List<Menu> menuTree =  builderMenuTree(menus,0l);
        List<MenuTreeVo> menuTreeVos = new ArrayList<>();
        for (Menu menu : menuTree) {
            MenuTreeVo menuTreeVo = new MenuTreeVo();
            menuTreeVo.setChildren(getChildrenVo(menu));
            menuTreeVo.setId(menu.getId());
            menuTreeVo.setLabel(menu.getMenuName());
            menuTreeVo.setParentId(menu.getParentId());
            menuTreeVos.add(menuTreeVo);
        }
        return ResponseResult.okResult(menuTreeVos);
    }


    @Override
    public List<MenuTreeVo> treeSelect1() {
        List<Menu> menus = list(null);
        List<Menu> menuTree =  builderMenuTree(menus,0l);
        List<MenuTreeVo> menuTreeVos = new ArrayList<>();
        for (Menu menu : menuTree) {
            MenuTreeVo menuTreeVo = new MenuTreeVo();
            menuTreeVo.setChildren(getChildrenVo(menu));
            menuTreeVo.setId(menu.getId());
            menuTreeVo.setLabel(menu.getMenuName());
            menuTreeVo.setParentId(menu.getParentId());
            menuTreeVos.add(menuTreeVo);
        }
        return menuTreeVos;
    }

    @Override
    public ResponseResult roleMenuTreeselect(Long id) {
        List<MenuTreeVo> menuTreeVos = treeSelect1();
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> roleMenus = roleMenuService.list(wrapper);
        List<Long> menuIds = roleMenus.stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());
        RoleVo2 roleVo2 = new RoleVo2();
        roleVo2.setMenus(menuTreeVos);
        roleVo2.setCheckedKeys(menuIds);


        return ResponseResult.okResult(roleVo2);
    }


    private List<MenuTreeVo> getChildrenVo(Menu menu) {
        if(menu == null){
            return null;
        }
        List<MenuTreeVo> menuTreeVos = new ArrayList<>();
        if(menu.getChildren() == null){
            MenuTreeVo menuTreeVo = new MenuTreeVo();
            menuTreeVo.setChildren(null);
            menuTreeVo.setId(menu.getId());
            menuTreeVo.setLabel(menu.getMenuName());
            menuTreeVo.setParentId(menu.getParentId());
            menuTreeVos.add(menuTreeVo);
        }else{
            for (Menu child : menu.getChildren()) {
                MenuTreeVo menuTreeVo = new MenuTreeVo();
                menuTreeVo.setChildren(getChildrenVo(child));
                menuTreeVo.setId(child.getId());
                menuTreeVo.setLabel(child.getMenuName());
                menuTreeVo.setParentId(child.getParentId());
                menuTreeVos.add(menuTreeVo);
            }
        }





        return menuTreeVos;
    }


    private List<Menu> builderMenuTree(List<Menu> menus, long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());


        return menuTree;
    }
    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(menu1 -> menu1.getParentId().equals(menu.getId()))
                .map(menu1 -> menu1.setChildren(getChildren(menu1, menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        //检查该用户role  id
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getUserId,userId);
        UserRole userRole = userRoleService.getOne(lambdaQueryWrapper);
        //如果是管理员，返回所有的权限
        List<Menu> menus = null;
        if(userRole.getRoleId() == 1l){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建tree
        //先找出第一层的菜单，然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
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
