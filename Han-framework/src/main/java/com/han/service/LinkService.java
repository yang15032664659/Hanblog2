package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.domain.ResponseResult;
import com.han.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();

    ResponseResult allLink(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(Link link);

    ResponseResult selectLink(Long id);

    ResponseResult updateLink(Link link);

    ResponseResult deleteLink(Long id);
}
