package com.han.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.han.utils.SecurityUtils;
import org.apache.catalina.security.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

import static com.han.utils.SecurityUtils.getAuthentication;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;//表示是自己创建
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //判空的问题，因为自动更新是系统后台做的，
        // 而SecurityContextHolder.getContext().setAuthentication()是前台发起请求时候做的，
        // 所以导致mp获取用户id的时候其实getAuthentication()根本就不存在为null。因此在这里判空就行了。
        if (Objects.isNull(getAuthentication())) {
            return;
        }

        if (Objects.nonNull(SecurityUtils.getUserId())) {
            this.setFieldValByName("updateTime", new Date(), metaObject);
            this.setFieldValByName(" ", SecurityUtils.getUserId(), metaObject);
        }
//        this.setFieldValByName("updateTime", new Date(), metaObject);
//        this.setFieldValByName(" ", SecurityUtils.getUserId(), metaObject);
    }
}