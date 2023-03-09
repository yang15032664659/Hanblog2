package com.han.controller;

import com.han.domain.ResponseResult;
import com.han.domain.dto.RoleDto;
import com.han.entity.Role;
import com.han.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleControler {
    @Autowired
    private RoleService roleService;
    @GetMapping("/list")
    public ResponseResult allRole(Integer pageNum,Integer pageSize,String roleName,String status){
        return roleService.allRole(pageNum,pageSize,roleName,status);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleDto roleDto){
        return roleService.changeStatus(roleDto);
    }
    @PostMapping
    public ResponseResult addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }
    @GetMapping("/{id}")
    public ResponseResult selectRoleById(@PathVariable Long id){
        return roleService.selectRoleById(id);
    }
    @PutMapping
    public ResponseResult updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id){
        return roleService.deleteRole(id);
    }
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }

}
