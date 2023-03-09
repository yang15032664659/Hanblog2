package com.han.controller;

import com.han.domain.ResponseResult;
import com.han.domain.dto.AddArticleDto;
import com.han.service.ArticleService;
import com.han.vo.ArticleByIdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto){
        return articleService.addArticle(addArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult allArticle(Integer pageNum,Integer pageSize,String title,String summary){
        return articleService.allArticle(pageNum,pageSize,title,summary);
    }

    @GetMapping("/{id}")
    public ResponseResult selectByArticleId(@PathVariable Long id){
        return articleService.selectByArticleId(id);
    }

    @PutMapping()
    public ResponseResult updateArticle(@RequestBody ArticleByIdVo article){
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        return articleService.deleteArticle(id);

    }
}
