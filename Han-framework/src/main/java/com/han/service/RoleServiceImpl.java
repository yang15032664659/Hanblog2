package com.han.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.constants.SystemConstants;
import com.han.domain.ResponseResult;
import com.han.domain.dto.RoleDto;
import com.han.entity.Role;
import com.han.entity.RoleMenu;
import com.han.entity.UserRole;
import com.han.mapper.RoleMapper;

import com.han.mapper.RoleMenuMapper;
import com.han.utils.BeanCopyUtils;
import com.han.vo.PageVo;
import com.han.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;

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

    @Override
    public ResponseResult allRole(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status),Role::getStatus,status);
        wrapper.eq(Role::getDelFlag, SystemConstants.STATUS_NORMAL);

        wrapper.like(StringUtils.hasText(roleName),Role::getRoleName,roleName);
        wrapper.orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,wrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleDto roleDto) {
        Role role = getById(roleDto.getRoleId());
        role.setStatus(roleDto.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(Role role) {
        save(role);
        List<Long> menuIds = role.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(id -> new RoleMenu(role.getId(), id))
                .collect(Collectors.toList());

        for (RoleMenu roleMenu : roleMenus) {
            roleMenuMapper.insert(roleMenu);
        }

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectRoleById(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role,RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    public ResponseResult updateRole(Role role) {
        updateById(role);
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,role.getId());
        roleMenuService.remove(wrapper);
        //todo 需要改进  不能一修改就删除完在添加吧
        List<Long> menuIds = role.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        Role role = getById(id);
        role.setDelFlag("1");
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus,SystemConstants.STATUS_NORMAL);
        wrapper.eq(Role::getDelFlag,SystemConstants.STATUS_NORMAL);
        List<Role> roles = list(wrapper);
        return ResponseResult.okResult(roles);
    }


}
