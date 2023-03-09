package com.han.vo;

import com.han.entity.Role;
import com.han.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSelectVo {
    private List<Long> roleIds;
    private List<Role> roles;
    private User user;
}
