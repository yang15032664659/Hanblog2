package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.domain.ResponseResult;
import com.han.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();
}
