package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long id);
}
