package com.han.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.constants.SystemConstants;
import com.han.domain.ResponseResult;
import com.han.entity.Link;
import com.han.mapper.LinkMappper;
import com.han.utils.BeanCopyUtils;
import com.han.vo.AllLinkVo;
import com.han.vo.LinkVo;
import com.han.vo.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMappper, Link> implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper();
        //审核通过
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(lambdaQueryWrapper);
        //封装VO
        List<AllLinkVo> allLinkVos = BeanCopyUtils.copyBeanList(links, AllLinkVo.class);
        //返回数据
        return ResponseResult.okResult(allLinkVos);
    }

    @Override
    public ResponseResult allLink(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Link::getDelFlag,SystemConstants.STATUS_NORMAL);
        wrapper.eq(StringUtils.hasText(status),Link::getStatus,status);
        wrapper.like(StringUtils.hasText(name),Link::getName,name);
        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,wrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getCurrent());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(Link link) {
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectLink(Long id) {
        Link link = getById(id);
        LinkVo linkVo = BeanCopyUtils.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult updateLink(Link link) {

        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        Link link = getById(id);
        link.setDelFlag(1);
        updateById(link);
        return ResponseResult.okResult();
    }
}
