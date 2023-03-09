package com.han.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.han.domain.ResponseResult;
import com.han.domain.dto.CategoryDto;
import com.han.entity.Category;
import com.han.enums.AppHttpCodeEnum;
import com.han.service.CategoryService;
import com.han.utils.BeanCopyUtils;
import com.han.utils.WebUtils;
import com.han.vo.CateGoryVo3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

//    @GetMapping("/list")
//    public ResponseResult allList(Integer pageNum, Integer pageSize, Category category){
//        return categoryService.allList(pageNum,pageSize,category);
//    }
    @GetMapping("/list")
    public ResponseResult allList(Integer pageNum, Integer pageSize,String name,String status){
        return categoryService.allList(pageNum,pageSize,name,status);
    }
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){

        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            List<Category> list = categoryService.list(null);
            //获取需要导出的数据
            List<CateGoryVo3> cateGoryVo3s = BeanCopyUtils.copyBeanList(list,CateGoryVo3.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), CateGoryVo3.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(cateGoryVo3s);
        } catch (Exception e) {
            //出现异常返回json
            WebUtils.renderString(response, JSON.toJSONString(ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR)));
        }

    }
    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }
    @GetMapping("/{id}")
    public ResponseResult selectCategory(@PathVariable Long id){
        return categoryService.selectCategory(id);
    }
    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(categoryDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }

}
