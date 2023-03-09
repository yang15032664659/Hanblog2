package com.han.controller;

import com.han.domain.ResponseResult;
import com.han.domain.dto.TagListDto;
import com.han.entity.Tag;
import com.han.service.TagService;
import com.han.vo.PageVo;
import com.han.vo.TagVo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.list(pageNum,pageSize,tagListDto);

    }
    @PostMapping
    public ResponseResult insertTag(@RequestBody Tag tag){
        return tagService.insertTag(tag);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult listById(@PathVariable("id") Long id){
        return tagService.listById(id);
    }
    @PutMapping
    public ResponseResult updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

}

