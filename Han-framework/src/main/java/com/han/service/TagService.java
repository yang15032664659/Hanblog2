package com.han.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.domain.ResponseResult;
import com.han.domain.dto.TagListDto;
import com.han.entity.Tag;
import com.han.vo.PageVo;
import com.han.vo.TagVo;

public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult insertTag(Tag tag);

    ResponseResult deleteTag(Long id);

    ResponseResult updateTag(Tag tag);



    ResponseResult listById(Long id);

    ResponseResult listAllTag();
}
