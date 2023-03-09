package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.domain.ResponseResult;
import com.han.entity.Menu;
import com.han.vo.MenuTreeVo;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult allMenu(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult selectMenuById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long id);

    ResponseResult treeSelect();
    List<MenuTreeVo> treeSelect1();

    ResponseResult roleMenuTreeselect(Long id);
}
