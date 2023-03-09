package com.han.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.han.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    List<String> selectRoleKeyByUserId(Long id);
}
//@Repository
//public interface RoleMapper extends BaseMapper<Role> {
//
//    List<String> selectRoleKeyByUserId(Long userId);
//}