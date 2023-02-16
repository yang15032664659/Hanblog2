package com.han.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.constants.SystemConstants;
import com.han.domain.ResponseResult;
import com.han.entity.Link;
import com.han.mapper.LinkMappper;
import com.han.utils.BeanCopyUtils;
import com.han.vo.AllLinkVo;
import org.springframework.stereotype.Service;

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
}
