package com.han.vo;

import com.han.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo2 {
    private List<MenuTreeVo> menus;
    private List<Long> checkedKeys;
}
