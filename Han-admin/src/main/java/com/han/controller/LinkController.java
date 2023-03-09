package com.han.controller;

import com.han.domain.ResponseResult;
import com.han.entity.Link;
import com.han.service.LinkService;
import com.han.vo.LinkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.MediaSize;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/list")
    public ResponseResult allLink(Integer pageNum,Integer pageSize,String name,String status){
        return linkService.allLink(pageNum,pageSize, name,status);
    }
    @PostMapping
    public ResponseResult addLink(@RequestBody Link link){
        return linkService.addLink(link);
    }
    @GetMapping("/{id}")
    public ResponseResult selectLink(@PathVariable Long id){
        return linkService.selectLink(id);
    }
    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        return linkService.updateLink(link);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        return linkService.deleteLink(id);
    }

}
