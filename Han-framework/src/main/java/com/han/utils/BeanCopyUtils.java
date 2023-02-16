package com.han.utils;

import com.han.entity.Article;
import com.han.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils(){

    }
    public static <T> T copyBean(Object source,Class<T> tClass) {
        //创建目标对象
        T result = null;
        try {
            result = tClass.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //返回结果
        return result;
    }
    public static <V,T>List<T> copyBeanList(List<V> list, Class<T> tClass){
        return list.stream()
                .map(o -> copyBean(o, tClass))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("yang");
        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
        System.out.println(hotArticleVo);


    }

}
