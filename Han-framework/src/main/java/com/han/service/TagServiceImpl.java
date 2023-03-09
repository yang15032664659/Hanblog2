package com.han.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.constants.SystemConstants;
import com.han.domain.ResponseResult;
import com.han.domain.dto.TagListDto;
import com.han.entity.Category;
import com.han.entity.Tag;
import com.han.mapper.TagMapper;
import com.han.utils.BeanCopyUtils;
import com.han.vo.PageVo;
import com.han.vo.TagVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper,Tag> implements TagService{
    @Override
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        wrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        wrapper.eq(Tag::getDelFlag, SystemConstants.STATUS_NORMAL);
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,wrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult insertTag(Tag tag) {
        if(StringUtils.hasText(tag.getName()) && StringUtils.hasText(tag.getRemark())){
            save(tag);
        }
        return ResponseResult.okResult(tag);
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        Tag tag = getById(id);
        tag.setDelFlag(1);

        if(updateById(tag)){
            return ResponseResult.okResult();
        }else{
            return ResponseResult.errorResult(405,"删除失败");
        }


    }

    @Override
    public ResponseResult updateTag(Tag tag) {
        updateById(tag);
        return ResponseResult.okResult(tag);
    }



    @Override
    public ResponseResult listById(Long id) {
        Tag tag = getById(id);

        TagVo tagVo = BeanCopyUtils.copyBean(tag,TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getDelFlag,SystemConstants.STATUS_NORMAL);

        List<Tag> list = list(wrapper);
        List<Tag> tags = BeanCopyUtils.copyBeanList(list, Tag.class);
        return ResponseResult.okResult(tags);
    }

}
